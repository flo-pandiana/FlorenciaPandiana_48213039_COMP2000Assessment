import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Grid<T> implements Iterable<Grid.Entry<T>> {
    private int rows;
    private int columns;
    private List<List<T>> cells;

    public Grid(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                "Rows and columns must be greater than zero"
            );
        }

        this.rows = rows;
        this.columns = columns;
        this.cells = new ArrayList<List<T>>();

        // Create each row
        for (int row = 0; row < rows; row++) {
            List<T> currentRow = new ArrayList<T>();

            // create columns in rows
            for (int column = 0; column < columns; column++) {
                currentRow.add(null);
            }

            cells.add(currentRow);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public T getCell(int row, int column) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        return cells.get(row).get(column);
    }

    public void setCell(int row, int column, T value) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        cells.get(row).set(column, value);
    }

    public void fill(T value) {
        for (Entry<T> entry : this) {
            setCell(entry.getRow(), entry.getColumn(), value);
        }
    }

    // Walks every cell row by row, so callers don't need nested loops
    @Override
    public Iterator<Entry<T>> iterator() {
        return new Iterator<Entry<T>>() {
            private int next = 0;

            @Override
            public boolean hasNext() {
                return next < rows * columns;
            }

            @Override
            public Entry<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                int row = next / columns;
                int column = next % columns;
                next++;

                return new Entry<T>(row, column, getCell(row, column));
            }
        };
    }

    public boolean isInBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public List<T> neighbours(int row, int column) {
        return neighbourPositions(row, column).stream()
            .map(position -> getCell(position.getRow(), position.getColumn()))
            .collect(Collectors.toList());
    }

    public List<Position> neighbourPositions(int row, int column) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        List<Position> positions = new ArrayList<Position>();

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {

                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                int neighbourRow = row + rowOffset;
                int neighbourColumn = column + columnOffset;

                if (isInBounds(neighbourRow, neighbourColumn)) {
                    positions.add(new Position(neighbourRow, neighbourColumn));
                }
            }
        }
        return positions;
    }

    public static class Position {
        private int row;
        private int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    public static class Entry<T> {
        private int row;
        private int column;
        private T value;

        public Entry(int row, int column, T value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public T getValue() {
            return value;
        }
    }
}
