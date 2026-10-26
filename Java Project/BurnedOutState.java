public class BurnedOutState implements CellState {
    @Override
    public void ignite(Cell cell, int intensity) {
        // A burned out cell can never reignite
    }

    @Override
    public void extinguish(Cell cell) {
        // Already out
    }

    @Override
    public void update(Cell cell) {
        // Nothing left to burn
    }

    @Override
    public boolean canBurn(Cell cell) {
        return false;
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public boolean isBurnedOut() {
        return true;
    }
}
