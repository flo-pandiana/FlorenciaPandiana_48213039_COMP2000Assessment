public class UnburntState implements CellState {
    @Override
    public void ignite(Cell cell, int intensity) {
        cell.startFire(intensity);
    }

    @Override
    public void extinguish(Cell cell) {
        // Nothing is burning, so there is nothing to extinguish
    }

    @Override
    public void update(Cell cell) {
        // Nothing to update until the cell is ignited
    }

    @Override
    public boolean canBurn(Cell cell) {
        return cell.hasTerrain() && cell.getTerrain().canBurn();
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public boolean isBurnedOut() {
        return false;
    }
}
