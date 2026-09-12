public class Rain extends Weather {

	public Rain(int strength, int duration, int[][] location) {
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation) {
		Grid<Cell> grid = simulation.getGrid();

		for (int row = 0; row < grid.getRows(); row++) {
			for (int column = 0; column < grid.getColumns(); column++) {
				Cell cell = grid.getCell(row, column);

				if (cell.isBurning() && Math.random() < 0.3) {
					cell.extinguish();
					simulation.removeHeat(row, column, strength);
				} else if (!cell.isBurning()) {
					simulation.removeHeat(row, column, strength / 2f);
				}
			}
		}
	}
}