public class BurningState implements CellState {
    private static final int BURN_DURATION = 12;

    @Override
    public void ignite(Cell cell, int intensity) {
        cell.startFire(intensity);
    }

    @Override
    public void extinguish(Cell cell) {
        cell.fire = null;
        cell.setState(new BurnedOutState());
    }

    @Override
    public void update(Cell cell) {
        if (!cell.hasTerrain()) return;
        if (!cell.getTerrain().canBurn()) return;

        Fire fire = cell.getFire();

        cell.getTerrain().burn(fire.getIntensity());
        fire.incrementTicksBurning(); //age the fire by one tick

        int elapsed = fire.getTicksBurning();

        if (elapsed >= BURN_DURATION) {
            cell.getTerrain().burn(250); //force remaining fuel to 0
            cell.fire = null;
            cell.setState(new BurnedOutState()); //cell can never reignite
            return;
        }

        //fade intensity from 10 down to 1 as elapsed approaches burn duration
        int newIntensity = Math.max(1, (int) Math.round(10 - (elapsed * 10.0 / BURN_DURATION)));
        fire.setIntensityAbsolute(newIntensity);
    }

    @Override
    public boolean canBurn(Cell cell) {
        return cell.hasTerrain() && cell.getTerrain().canBurn();
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    public boolean isBurnedOut() {
        return false;
    }
}
