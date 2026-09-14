public class Cell {
    Terrain terrain;
    Fire fire;
    private boolean permanentlyBurnedOut = false;

    public Cell() {
        terrain = null;
        fire = null;
    }

    public Cell(Terrain t) {
        terrain = t;
        fire = null;
    }

    public Cell(Terrain t, Fire f) {
        terrain = t;
        fire = f;
    }

    public Cell(Fire f) {
        terrain = null;
        fire = f;
    }

    public Boolean hasTerrain() {
        return terrain != null;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean isBurning() {
        return fire != null;
    }

    public void extinguish() {
        if (!isBurning()) {
            return;
        }
        permanentlyBurnedOut = true;
        fire = null;
    }

    public boolean canBurn() {
        if (permanentlyBurnedOut) {
            return false;
        }
        if (terrain != null) {
            return terrain.canBurn();
        }
        return false;
    }

    public void updateBurningState() {
        if (!hasTerrain() || !isBurning()) return;
        if (!terrain.canBurn()) return;

        terrain.burn(fire.getIntensity());
        fire.incrementTicksBurning(); //age the fire by one tick

        int burnDuration = 12;
        int elapsed = fire.getTicksBurning();

        if (elapsed >= burnDuration) {
            terrain.burn(250); //force remaining fuel to 0
            permanentlyBurnedOut = true; //cell can never reignite
            fire = null;
            return;
        }

        //fade intensity from 10 down to 1 as elapsed approaches burnDuration
        int newIntensity = Math.max(1, (int) Math.round(10 - (elapsed * 10.0 / burnDuration)));
        fire.setIntensityAbsolute(newIntensity);
    }

    public void ignite(int intensity) {
        if (permanentlyBurnedOut) {
            return;
        }
        if (!hasTerrain())
            throw new IllegalStateException("Attempted to ignite a cell with no terrain.");
        fire = new Fire(intensity);
        terrain.ignite();
    }

    public Fire getFire() {
        return fire;
    }

    public boolean isPermanentlyBurnedOut() {
        return permanentlyBurnedOut;
    }
}