package niconiconyx.hungersdelight.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import niconiconyx.hungersdelight.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerDelight {
    @Shadow private int foodLevel;
    @Shadow private float exhaustion;
    @Shadow private float saturationLevel;
    @Shadow private int foodTickTimer;
    @Shadow public abstract void addExhaustion(float exhaustion);
    @Shadow public abstract void setFoodLevel(int foodLevel);
    @Shadow public abstract void setSaturationLevel(float saturationLevel);
    @Shadow public abstract float getSaturationLevel();
    @Shadow public abstract int getFoodLevel();
    @Shadow private void add(int food, float exhaustion){}

    @Unique private int passiveHungerTickTimer = 0;
    @Unique private int shieldHungerTickTimer = 0;

    @Redirect(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;add(IF)V"))
    private void modifyFoodComponent(HungerManager manager, int food, float saturation) {
        add(Math.round(food * Config.hungerFromFoodMultiplier), saturation * Config.saturationFromFoodMultiplier);
    }

    @ModifyVariable(method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"), ordinal = 0)
    public Difficulty hungerOnPeaceful(Difficulty originalDifficulty) {
        if (originalDifficulty.equals(Difficulty.PEACEFUL) && !Config.hungerInPeaceful) {
            return Difficulty.EASY;
        }
        return originalDifficulty;
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(PlayerEntity player, CallbackInfo ci) {
        Difficulty difficulty = player.getWorld().getDifficulty();
        if (difficulty == Difficulty.PEACEFUL && !Config.hungerInPeaceful)
            return;
        if (player.getAbilities().invulnerable)
            return;
        //If hunger is not a thing, then do not execute the rest of the code below and for source HungerManager


        passiveHungerTickTimer++;
        if (passiveHungerTickTimer >= Config.passiveExhaustionTickRate){
            passiveHungerTickTimer = 0;
            exhaustion += Config.passiveExhaustionAmount;
            addExhaustion(exhaustion);
        }

        if(!Config.disableShieldExhaustion){
            if (player.getActiveItem().getItem() == Items.SHIELD){
                shieldHungerTickTimer++;
                if (shieldHungerTickTimer >= Config.shieldExhaustionTickRate){
                    exhaustion += Config.shieldExhaustionAmount;
                    addExhaustion(exhaustion);
                }
            }
        }


    }

    //LIVING ENTITY INJECTIONS
    @Mixin(LivingEntity.class)
    public static abstract class LivingEntityDelight extends Entity {
        @Unique private long startSleep;

        public LivingEntityDelight(EntityType<?> type, World world) {
            super(type, world);
        }

        @Inject(method = "sleep", at = @At("TAIL"))
        private void getStartSleepTime(CallbackInfo info) {
            if ((Object)this instanceof PlayerEntity) {
                startSleep = this.getWorld().getTimeOfDay() % 24000;
            }
        }

        @Inject(method = "wakeUp", at = @At("TAIL"))
        private void applySleepExhaustion(CallbackInfo info) {
            if ((Object)this instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity)(Object)this;
                ModifyHungerLevelDuringSleep(player);
                startSleep = 0;
            }
        }


        public void ModifyHungerLevelDuringSleep(PlayerEntity player){
            if (Config.useDynamicSleepHungerCost){
                //Checks the total energy the player has, which is 4 per sat/food level, and checks if the current level would put the player at 0
                if(((player.getHungerManager().getFoodLevel() + player.getHungerManager().getSaturationLevel()) * 4) - Math.round(Math.abs((this.getWorld().getTimeOfDay() % 24000) - startSleep) / 1000.0F) * Config.dynamicSleepHungerCost == 0){
                    player.getHungerManager().setFoodLevel(2); //if it puts the player at 0, it will leave them with 1 drumstick of hunger on wakeup.
                }
                else {
                    //Calculates the amount of exhaustion based on how many in-game hours the player sleeps (1 hour = 1000 ticks), the more ticks you sleep the more hungry you wake up.
                    float exhaustion = Math.round(Math.abs((this.getWorld().getTimeOfDay() % 24000) - startSleep) / 1000.0F) * Config.dynamicSleepHungerCost;
                    player.getHungerManager().addExhaustion(exhaustion);
                }


            }
            else{
                player.getHungerManager().setSaturationLevel(0);
                player.getHungerManager().setFoodLevel(Config.hungerOnWakingUp);
            }
        }

    }
}
