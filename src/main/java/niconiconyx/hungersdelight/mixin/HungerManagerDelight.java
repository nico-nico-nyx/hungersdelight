package niconiconyx.hungersdelight.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import niconiconyx.hungersdelight.HungersDelight;
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

        if(Config.usePassiveExhaustion){
            if (passiveHungerTickTimer >= Config.passiveExhaustionTickRate){
                exhaustion += Config.passiveExhaustionAmount;
                addExhaustion(exhaustion);
                passiveHungerTickTimer = 0;
            }
            else{
                passiveHungerTickTimer++;
            }
        }



        if(Config.useShieldExhaustion){
            if (player.getActiveItem().getItem() == Items.SHIELD){
                shieldHungerTickTimer++;
                if (shieldHungerTickTimer >= Config.shieldExhaustionTickRate){
                    exhaustion += Config.shieldExhaustionAmount;
                    addExhaustion(exhaustion);
                }
            }
        }


    }
}
