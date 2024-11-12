package niconiconyx.hungersdelight.config;

public class Config extends ConfigBuilder
{
    public static int spawnHealth = 20;
    public static int spawnHunger = 20;

    public static int passiveExhaustionTickRate = 80;
    public static float passiveExhaustionAmount = 0.1F;

    public static float jumpExhaustionAmount = 0.05F;
    public static float sprintJumpExhaustionAmount = 0.2F;
    public static int minFoodLevelForSprint = 6;
    public static float walkingExhaustionMultiplier = 0.0F;
    public static float sprintingExhaustionMultiplier = 0.1F;
    public static float crouchingExhaustionMultiplier = 0.0F;
    public static float swimmingExhaustionMultiplier = 0.1F;
    public static float walkingUnderwaterExhaustionMultiplier = 0.1F;
    public static float walkingOnWaterExhaustionMultiplier = 0.1F;
    public static boolean disableShieldExhaustion = false;
    public static int shieldExhaustionTickRate = 80;
    public static float shieldExhaustionAmount = 0.0F;
    public static boolean useDynamicSleepHungerCost = false;
    public static int hungerOnWakingUp = 10;
    public static float dynamicSleepHungerCost = 0.0F;

    public static float hungerFromFoodMultiplier = 1.0F;
    public static float saturationFromFoodMultiplier = 1.0F;
    public static boolean hungerInPeaceful = true;

}
