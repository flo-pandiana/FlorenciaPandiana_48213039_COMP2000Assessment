public class Lightning extends Weather {

	private int strikesPerUpdate;

	public Lightning(int strength, int duration, int[][] location, int strikesPerUpdate) {
		super(strength, duration, location);
		this.strikesPerUpdate = strikesPerUpdate;
	}

	public int getStrikesPerUpdate() {
		return strikesPerUpdate;
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation) {
		Grid<Cell> grid = simulation.getGrid();

		for (int i = 0; i < strikesPerUpdate; i++) {
			int row = (int) (Math.random() * grid.getRows());
			int column = (int) (Math.random() * grid.getColumns());

			Cell cell = grid.getCell(row, column);

			if (cell.canBurn() && !cell.isBurning()) {
				simulation.igniteCell(row, column, strength);
			}
		}
	}
}
