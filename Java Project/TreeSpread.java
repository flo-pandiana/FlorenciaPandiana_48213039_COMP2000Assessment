public class TreeSpread extends BaseSpread {
    private final Tree tree;

    public TreeSpread(Tree tree) {
        this.tree = tree;
    }

    @Override
    public float calculate(int fireIntensity, float moisture, int age) {
        return super.calculate(fireIntensity, moisture, age) + tree.getHeight();
    }
}
