package hk.ust.cse.comp3021.pa1.model;

import org.jetbrains.annotations.NotNull;

/**
 * A single cell on the game board.
 */
public abstract sealed class Cell implements BoardElement permits EntityCell, Wall {

    @NotNull
    private final Position position;

    /**
     * Creates an instance of {@link Cell} at the given position on the game board.
     *
     * @param position The position where this cell belongs at.
     */
    protected Cell(@NotNull final Position position) {
        // TODO(DONE)
        this.position = position;
    }

    /**
     * @return The {@link Position} of this cell on the game board.
     */
    @NotNull
    public final Position getPosition() {
        // TODO(DONE)
        return this.position;
    }
    //to be deleted, just for checking
//    @Override
//    public String toString() {
//        return "Empty Cell";
//    }
}
