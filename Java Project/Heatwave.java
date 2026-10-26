public class Heatwave extends Weather {

	public Heatwave(int strength, int duration, int[][] location) {
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation) {
		Grid<Cell> grid = simulation.getGrid();

		grid.forEach(entry -> {
			if (!entry.getValue().isBurning()) {
				return;
			}

			int row = entry.getRow();
			int column = entry.getColumn();

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
		});
	}
}