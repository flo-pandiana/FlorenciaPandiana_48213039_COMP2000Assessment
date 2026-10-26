public class BaseSpread implements SpreadHeat {
    @Override
    public float calculate(int fireIntensity, float moisture, int age) {
        if (fireIntensity <= 0) {
            throw new IllegalArgumentException(
                    "Fire intensity must be greater than zero, got: " + fireIntensity);
        }

        float baseHeat = 15f; //starting heat value
        float moistureFactor = 1f - (moisture * 0.5f); //moisture ranges 0 to 1, so this produces a factor between 0.5 (very wet) and 1.0 (very dry)
        float ageFactor = Math.max(0.3f, 1.3f - (age * 0.06f)); // younger = more spread, older = less, min 0.3
        float intensityFactor = 0.5f + (fireIntensity / 20f); //stronger fires spread more heat
        return baseHeat * moistureFactor * ageFactor * intensityFactor; //combine all factors into the final spread heat
    }
}
