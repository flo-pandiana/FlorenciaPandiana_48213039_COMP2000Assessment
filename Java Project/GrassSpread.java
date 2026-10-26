public class GrassSpread extends BaseSpread {
    private final Grass grass;

    public GrassSpread(Grass grass) {
        this.grass = grass;
    }

    @Override
    public float calculate(int fireIntensity, float moisture, int age) {
        return super.calculate(fireIntensity, moisture, age) + grass.getDensity();
    }
}
