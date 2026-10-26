public class Cell {
    Terrain terrain;
    Fire fire;
    private CellState state;

    public Cell() {
        terrain = null;
        fire = null;
        state = new UnburntState();
    }

    public Cell(Terrain t) {
        terrain = t;
        fire = null;
        state = new UnburntState();
    }

    public Cell(Terrain t, Fire f) {
        terrain = t;
        fire = f;
        state = (f != null) ? new BurningState() : new UnburntState();
    }

    public Cell(Fire f) {
        terrain = null;
        fire = f;
        state = (f != null) ? new BurningState() : new UnburntState();
    }

    public Boolean hasTerrain() {
        return terrain != null;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    void setState(CellState state) {
        this.state = state;
    }

    public boolean isBurning() {
        return state.isBurning();
    }

    public void extinguish() {
        state.extinguish(this);
    }

    public boolean canBurn() {
        return state.canBurn(this);
    }

    public void updateBurningState() {
        state.update(this);
    }

    public void ignite(int intensity) {
        state.ignite(this, intensity);
    }

    // Called by the states once they decide the cell should catch fire
    void startFire(int intensity) {
        if (!hasTerrain())
            throw new IllegalStateException("Attempted to ignite a cell with no terrain.");
        fire = new Fire(intensity);
        state = new BurningState();
        terrain.ignite();
    }

    public Fire getFire() {
        return fire;
    }

    public boolean isPermanentlyBurnedOut() {
        return state.isBurnedOut();
    }
}
