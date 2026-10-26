public interface CellState {
    void ignite(Cell cell, int intensity);

    void extinguish(Cell cell);

    void update(Cell cell);

    boolean canBurn(Cell cell);

    boolean isBurning();

    boolean isBurnedOut();
}
