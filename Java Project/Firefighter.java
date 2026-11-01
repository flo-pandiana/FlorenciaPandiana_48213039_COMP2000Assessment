public class Firefighter implements WeatherObserver {
    private static final int NORMAL_SPEED = 1;
    private static final float COOLING = 60f; // heat removed around the firefighter each tick

    private int row;
    private int column;
    private int speed = NORMAL_SPEED; // cells moved per tick

    public Firefighter(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void act(ForestFireSimulation simulation) {
        Grid<Cell> grid = simulation.getGrid();

        Grid.Entry<Cell> target = null;

        for (Grid.Entry<Cell> entry : grid) {
            if (!entry.getValue().isBurning()) {
                continue;
            }

            if (target == null || distanceTo(entry) < distanceTo(target)) {
                target = entry;
            }
        }

        if (target == null) {
            return;
        }

        for (int step = 0; step < speed && distanceTo(target) > 1; step++) {
            row += Integer.signum(target.getRow() - row);
            column += Integer.signum(target.getColumn() - column);
        }

        fightFiresAround(simulation);
    }

    // Puts out burning cells next to the firefighter and cools the area so it doesn't reignite
    private void fightFiresAround(ForestFireSimulation simulation) {
        Grid<Cell> grid = simulation.getGrid();

        for (Grid.Position neighbour : grid.neighbourPositions(row, column)) {
            Cell cell = grid.getCell(neighbour.getRow(), neighbour.getColumn());

            if (cell.isBurning()) {
                cell.extinguish();
            }

            simulation.removeHeat(neighbour.getRow(), neighbour.getColumn(), COOLING);
        }

        Cell here = grid.getCell(row, column);

        if (here.isBurning()) {
            here.extinguish();
        }

        simulation.removeHeat(row, column, COOLING);
    }

    // Chebyshev distance, since firefighters can move diagonally
    private int distanceTo(Grid.Entry<Cell> entry) {
        return Math.max(Math.abs(entry.getRow() - row), Math.abs(entry.getColumn() - column));
    }

    @Override
    public void weatherStarted(Weather weather) {
        if (weather instanceof Rain) {
            speed = 2; // rain makes the job easier, so they move faster
        } else if (weather instanceof Heatwave) {
            speed = 0; // too hot to move, they only fight fires right next to them
        }
    }

    @Override
    public void weatherEnded(Weather weather) {
        if (weather instanceof Rain || weather instanceof Heatwave) {
            speed = NORMAL_SPEED;
        }
    }
}
