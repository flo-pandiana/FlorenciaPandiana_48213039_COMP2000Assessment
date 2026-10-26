public class TreeSpread implements SpreadHeat {
    private final SpreadHeat inner;
    private final Tree tree;

    public TreeSpread(SpreadHeat inner, Tree tree) {
        this.inner = inner;
        this.tree = tree;
    }

    @Override
    public float calculate(int fireIntensity, float moisture, int age) {
        return inner.calculate(fireIntensity, moisture, age) + tree.getHeight();
    }
}
