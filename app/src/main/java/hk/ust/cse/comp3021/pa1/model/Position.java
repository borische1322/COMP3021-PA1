package hk.ust.cse.comp3021.pa1.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A position on the game board.
 */
public record Position(int row, int col) {

    /**
     * @param row The row number on the game board.
     * @param col The column number on the game board.
     * @throws IllegalArgumentException if any component of the coordinate is negative.
     */
    public Position {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be of a negative value.");
        }
    }

    /**
     * Creates a new instance of {@link Position} with the coordinates offset by the given amount.
     *
     * @param dRow Number of rows to offset by.
     * @param dCol Number of columns to offset by.
     * @return A new instance of {@link Position} with the given offset applied.
     * @throws IllegalArgumentException if any component of the resulting coordinate is negative.
     */
    @NotNull
    public Position offsetBy(final int dRow, final int dCol) {
        // TODO(DONE)
        var x = this.col + dCol;
        var y = this.row + dRow;
        if ( x < 0 || y < 0 ){
            throw new IllegalArgumentException("Offset is larger than current position resulting in negative value");
        }
        //System.out.println(x);
        //System.out.println(y);
        return new Position(y,x);
    }

    /**
     * Creates a new instance of {@link Position} with the coordinates offset by the given amount.
     *
     * @param offset The {@link PositionOffset} instance to offset this position by.
     * @return A new instance of {@link Position} with the given offset applied.
     * @throws IllegalArgumentException if any component of the resulting coordinate is negative.
     */
    @NotNull
    public Position offsetBy(@NotNull final PositionOffset offset) {
        // TODO(DONE)
        var x = this.col + offset.dCol();
        var y = this.row + offset.dRow();
        if ( x < 0 || y < 0 ){
            throw new IllegalArgumentException("Offset is larger than current position resulting in negative value");
        }
        return new Position(y,x);
    }

    /**
     * Creates a new instance of {@link Position} with the coordinates offset by the given amount. If the resulting
     * position is out-of-bounds (either because either coordinate is negative or exceeds {@code numRows} or
     * {@code numCols}), returns {@code null}.
     *
     * @param dRow    Number of rows to offset by.
     * @param dCol    Number of columns to offset by.
     * @param numRows Number of rows of the game board.
     * @param numCols Number of columns of the game board.
     * @return A new instance of {@link Position} with the given offset applied.
     */
    @Nullable
    public Position offsetByOrNull(final int dRow, final int dCol, final int numRows, final int numCols) {
        // TODO(DONE)
        var x = this.col - dCol;
        var y = this.row - dRow;
        if ( x < 0 || y < 0 || x > (numCols - 1) || y > (numRows - 1)){
            return null;
        }
        return new Position(y,x);
    }

    /**
     * Creates a new instance of {@link Position} with the coordinates offset by the given amount. If the resulting
     * position is out-of-bounds (either because either coordinate is negative or exceeds {@code numRows} or
     * {@code numCols}), returns {@code null}.
     *
     * @param offset  The {@link PositionOffset} instance to offset this position by.
     * @param numRows Number of rows of the game board.
     * @param numCols Number of columns of the game board.
     * @return A new instance of {@link Position} with the given offset applied.
     */
    @Nullable
    public Position offsetByOrNull(@NotNull final PositionOffset offset, final int numRows, final int numCols) {
        // TODO(DONE)
        var x = this.col - offset.dCol();
        var y = this.row - offset.dRow();
        if ( x < 0 || y < 0 || x > (numCols - 1) || y > (numRows - 1)){
            return null;
        }
        return new Position(y,x);
    }
}
