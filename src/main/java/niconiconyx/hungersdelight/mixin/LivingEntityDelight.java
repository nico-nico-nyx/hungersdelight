package niconiconyx.hungersdelight.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import niconiconyx.hungersdelight.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//LIVING ENTITY INJECTIONS
@Mixin(LivingEntity.class)
public abstract class LivingEntityDelight extends Entity {
    @Unique
    private long startSleep;

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
                if (player.getHungerManager().getFoodLevel() >= Config.hungerOnWakingUp){
                    player.getHungerManager().setFoodLevel(Config.hungerOnWakingUp);
                }

            }
            startSleep = 0;
        }
    }

}
