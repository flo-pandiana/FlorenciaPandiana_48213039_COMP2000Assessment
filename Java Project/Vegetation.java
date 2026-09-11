import Flammability.Burnable;

public abstract class Vegetation extends Terrain {
    private int age;
    private float moisture;

    public Vegetation(int age, int fuel, int burnRate, float moisture) {
        this.age = age;
        flammable = new Burnable(fuel, burnRate);
        this.moisture = moisture;
    }

    public int getAge() {
        return age;
    }

    public int getFuel() {
        return flammable.getFuel();
    }

    public int getBurnRate() {
        return flammable.getBurnRate();
    }

    public float getMoisture() {
        return moisture;
    }

    public void setMoisture(float moisture) {
        this.moisture = moisture;
    }

    public float calculateSpreadHeat(int fireIntensity) {
        if (fireIntensity <= 0) {
            throw new IllegalArgumentException(
                    "Fire intensity must be greater than zero, got: " + fireIntensity);
        }

        float baseHeat = 15f;
        float moistureFactor = 1f - (moisture * 0.5f);
        float intensityFactor = 0.5f + (fireIntensity / 20f);
        return baseHeat * moistureFactor * intensityFactor;
    }

    public void update() {
        age++;
    }

    @Override
    public void burn(int intensity) {
        flammable.burn(intensity, moisture);
    }
}