package niconiconyx.hungersdelight.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import niconiconyx.hungersdelight.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public class PlayerRespawn {

    @Redirect(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V"))
    private void SetSpawnHealthAndFood(ServerPlayerEntity player, float _base) {
        player.setHealth(Config.spawnHealth);
        player.getHungerManager().setFoodLevel(Config.spawnHunger);
    }

}
