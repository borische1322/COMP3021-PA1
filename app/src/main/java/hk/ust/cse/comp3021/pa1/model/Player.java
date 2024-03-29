package hk.ust.cse.comp3021.pa1.model;

import org.jetbrains.annotations.NotNull;

/**
 * The player entity on a game board.
 *
 * <p>
 * There should be at most one player entity on a game board.
 * </p>
 */
public final class Player extends Entity {

    /**
     * Creates an instance of {@link Player}, initially not present on any {@link EntityCell}.
     */
    public Player() {
        // TODO(DONE)
    }

    /**
     * Creates an instance of {@link Player}.
     *
     * @param owner The initial {@link EntityCell} the player belongs to.
     */
    public Player(@NotNull final EntityCell owner) {
        // TODO(DONE)
        this.setOwner(owner);
    }

    @Override
    public char toUnicodeChar() {
        return '\u25EF';
    }

    @Override
    public char toASCIIChar() {
        return '@';
    }

    //to be deleted, just for checking
//    @Override
//    public String toString() {
//        return "Player";
//    }
}
