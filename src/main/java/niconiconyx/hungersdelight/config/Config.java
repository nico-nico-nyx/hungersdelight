package niconiconyx.hungersdelight.config;

public class Config extends ConfigBuilder
{
    public static int spawnHealth = 20;
    public static int spawnHunger = 20;

    public static int passiveExhaustionTickRate = 80;
    public static float passiveExhaustionAmount = 0F;

    public static int starvationDamageTickRate = 80;
    public static float starvationDamageAmount = 1F;

    public static int foodRegenTickRate = 80;
    public static float foodRegenHealthAmount = 1.0F;
    public static float foodRegenExhaustionAmount = 6.0F;
    public static int foodRegenMinimumHunger = 18;

    public static int hyperFoodRegenTickRate = 10;
    public static float hyperFoodRegenHealthMultiplier = 1.0F;
    public static float hyperFoodRegenExhaustionMultiplier = 1.0F;
    public static int hyperFoodRegenMinimumHunger = 20;

    public static float jumpExhaustionAmount = 0.05F;
    public static float sprintJumpExhaustionAmount = 0.2F;
    public static int MinFoodLevelForSprint = 6;
    public static float walkingExhaustionMultiplier = 0.0F;
    public static float sprintingExhaustionMultiplier = 0.1F;
    public static float crouchingExhaustionMultiplier = 0.0F;
    public static float swimmingExhaustionMultiplier = 0.1F;
    public static float walkingUnderwaterExhaustionMultiplier = 0.1F;
    public static float walkingOnWaterExhaustionMultiplier = 0.1F;
    public static boolean disableRegenWhenUsingShield = false;
    public static int shieldExhaustionRate = 80;
    public static float shieldExhaustionAmount = 0.0F;
    public static boolean useDynamicSleepHungerCost = false;
    public static int hungerOnWakingUp = 10;
    public static float dynamicSleepHungerCost = 0.0F;

    public static float hungerFromFoodMultiplier = 1.0F;
    public static float saturationFromFoodMultiplier = 1.0F;

}
