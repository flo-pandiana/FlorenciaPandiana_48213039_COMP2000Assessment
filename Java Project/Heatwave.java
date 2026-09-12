public class Heatwave extends Weather {

	public Heatwave(int strength, int duration, int[][] location) {
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation) {
		Grid<Cell> grid = simulation.getGrid();

		for (int row = 0; row < grid.getRows(); row++) {
			for (int column = 0; column < grid.getColumns(); column++) {
				Cell cell = grid.getCell(row, column);

				if (!cell.isBurning()) {
					continue;
				}

				for (int rowOffset = -2; rowOffset <= 2; rowOffset++) {
					for (int columnOffset = -2; columnOffset <= 2; columnOffset++) {

						int targetRow = row + rowOffset;
						int targetColumn = column + columnOffset;

						if (!grid.isInBounds(targetRow, targetColumn)) {
							continue;
						}

						Cell target = grid.getCell(targetRow, targetColumn);

						if (target.canBurn() && !target.isBurning() && Math.random() < 0.03) {
							simulation.igniteCell(targetRow, targetColumn, strength);
						}
					}
				}
			}
		}
	}
}