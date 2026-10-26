public class GrassSpread implements SpreadHeat {
    private final SpreadHeat inner;
    private final Grass grass;

    public GrassSpread(SpreadHeat inner, Grass grass) {
        this.inner = inner;
        this.grass = grass;
    }

    @Override
    public float calculate(int fireIntensity, float moisture, int age) {
        return inner.calculate(fireIntensity, moisture, age) + grass.getDensity();
    }
}
