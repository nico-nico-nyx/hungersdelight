package niconiconyx.hungersdelight.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.HungerManager;
import niconiconyx.hungersdelight.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityDelight {
    @Redirect(method = "canSprint", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;getFoodLevel()I"))
    private int canSprintBasedOnFood(HungerManager manager) {
        //20 = true, 0 = false
        return manager.getFoodLevel() >= Config.minFoodLevelForSprint ? 20 : 0;
    }
}
