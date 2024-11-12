package niconiconyx.hungersdelight.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import niconiconyx.hungersdelight.config.Config;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityDelight {
    @Redirect(
            method = "Lnet/minecraft/entity/player/PlayerEntity;tickMovement()V",
            at = @At(value = "FIELD",
                    opcode = Opcodes.GETSTATIC,
                    target = "Lnet/minecraft/world/Difficulty;PEACEFUL:Lnet/minecraft/world/Difficulty;"))
    private Difficulty getDifficulty() {
        return Difficulty.NORMAL;
    }

    @ModifyArg(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V", ordinal = 0), index = 0)
    private float actionHunger$changeSprintJumpExhaustionAmount(float _original) {
        return Config.sprintJumpExhaustionAmount;
    }

    @ModifyArg(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V", ordinal = 1), index = 0)
    private float actionHunger$changeJumpExhaustionAmount(float _original) {
        return Config.jumpExhaustionAmount;
    }

    @Unique
    private float movementForExhaustion;

    @Inject(method = "increaseTravelMotionStats", at = @At("HEAD"))
    private void setMovementForExhaustion(double dx, double dy, double dz, CallbackInfo info) {
        movementForExhaustion = Math.round(MathHelper.sqrt((float) (dx * dx + dy * dy + dz * dz)) * 100.0F) * 0.01F;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeSwimExhaustionAmount(float _original) {
        return movementForExhaustion * Config.swimmingExhaustionMultiplier;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeWalkUnderWaterExhaustionAmount(float _original) {
        return movementForExhaustion * Config.walkingUnderwaterExhaustionMultiplier;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 2, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeWalkOnWaterExhaustionAmount(float _original) {
        return movementForExhaustion * Config.walkingOnWaterExhaustionMultiplier;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 3, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeSprintExhaustionAmount(float _original) {
        return movementForExhaustion * Config.sprintingExhaustionMultiplier;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 4, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeCrouchExhaustionAmount(float _original) {
        return movementForExhaustion * Config.crouchingExhaustionMultiplier;
    }

    @ModifyArg(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", ordinal = 5, target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"), index = 0)
    private float changeWalkExhaustionAmount(float _original) {
        return movementForExhaustion * Config.walkingExhaustionMultiplier;
    }


}
