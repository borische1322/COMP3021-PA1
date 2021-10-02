package hk.ust.cse.comp3021.pa1.controller;

import hk.ust.cse.comp3021.pa1.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller for {@link GameBoard}.
 *
 * <p>
 * This class is responsible for providing high-level operations to mutate a {@link GameBoard}. This should be the only
 * class which mutates the game board; Other classes should use this class to mutate the game board.
 * </p>
 */
public class GameBoardController {

    @NotNull
    private final GameBoard gameBoard;

    /**
     * Creates an instance.
     *
     * @param gameBoard The instance of {@link GameBoard} to control.
     */
    public GameBoardController(@NotNull final GameBoard gameBoard) {
        // TODO(DONE)
        this.gameBoard = gameBoard;
    }

    /**
     * Moves the player in the given direction.
     *
     * <p>
     * You should ensure that the game board is only mutated if the move is valid and results in the player still being
     * alive. If the player dies after moving or the move is invalid, the game board should remain in the same state as
     * before this method was called.
     * </p>
     *
     * @param direction Direction to move the player in.
     * @return An instance of {@link MoveResult} representing the result of this action.
     */
    @NotNull
    public MoveResult makeMove(@NotNull final Direction direction) {
        // TODO(DONE)
        //System.out.println("no");
        List<Position> collectedGems = new ArrayList<Position>();
        List<Position> collectedExtra = new ArrayList<Position>();
        int delta = 0;
        //System.out.println(gameBoard.getPlayer().getOwner());
        assert gameBoard.getPlayer().getOwner() != null;
        var playerPosition = gameBoard.getPlayer().getOwner().getPosition();
        var playerRow = playerPosition.row();
        var playerCol = playerPosition.col();
        int offsetR = 0;
        int offsetC = 0;
        //System.out.println(direction.getOffset().dRow());
        //System.out.println(direction.getOffset().dCol());
        if ((offsetR = direction.getOffset().dRow()) != 0){
            //System.out.println("HEY");
            playerRow += offsetR;
            delta ++;
            while(playerRow >= 0 && playerRow < gameBoard.getNumRows()){
                //System.out.println("HIHI");
                if (gameBoard.getCell(playerRow,playerCol) instanceof Wall){
                    //System.out.println("WALL");
                    if (delta == 1){
                        //System.out.println("WALL NEXT CELL NO MOVE");
                        return new MoveResult.Invalid(playerPosition);
                    }else{
                        //System.out.println("WALL MOVE");
                        //gameBoard.getEntityCell(playerRow-offsetR, playerCol).setEntity(gameBoard.getPlayer());
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(delta * offsetR-offsetR, 0), playerPosition,collectedGems,collectedExtra);
                    }
                } else if (gameBoard.getCell(playerRow,playerCol) instanceof EntityCell c){
                    if(c.getEntity() instanceof Gem){
                        //System.out.println("GEM");
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(null);
                        collectedGems.add(new Position(playerRow,playerCol));
                    }
                    if(c.getEntity() instanceof ExtraLife){
                        //System.out.println("EXTRA");
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(null);
                        collectedExtra.add(new Position(playerRow,playerCol));
                    }
                    if(c.getEntity() instanceof Mine){
                        //System.out.println("MINE");
                        collectedExtra.clear();
                        collectedGems.clear();
                        return new MoveResult.Valid.Dead(playerPosition, playerPosition.offsetBy(delta * offsetR, 0));
                    }else if (c instanceof StopCell){
                        //System.out.println("STOP");
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(gameBoard.getPlayer());
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(delta * offsetR, 0), playerPosition,collectedGems,collectedExtra);
                    }
                }
                if ((playerRow + offsetR) < 0 || (playerRow + offsetR)>=gameBoard.getNumRows()){
                    //System.out.println("HIHIHIHIHI");
                    //gameBoard.getEntityCell(playerRow, playerCol).setEntity(gameBoard.getPlayer());
                    return new MoveResult.Valid.Alive(playerPosition.offsetBy(delta * offsetR, 0), playerPosition,collectedGems,collectedExtra);
                }
                delta++;
                playerRow += offsetR;
            }

        }else if ((offsetC = direction.getOffset().dCol()) != 0){
            playerCol += offsetC;
            delta ++;
            while(playerCol >= 0 && playerCol < gameBoard.getNumCols()){
                if (gameBoard.getCell(playerRow,playerCol) instanceof Wall){
                    if (delta == 1){
                        return new MoveResult.Invalid(playerPosition);
                    }else{
                        //gameBoard.getEntityCell(playerRow, playerCol-offsetC).setEntity(gameBoard.getPlayer());
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(0, delta * offsetC-offsetC), playerPosition,collectedGems,collectedExtra);
                    }
                } else if (gameBoard.getCell(playerRow,playerCol) instanceof EntityCell c){
                    if(c.getEntity() instanceof Gem){
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(null);
                        collectedGems.add(new Position(playerRow,playerCol));
                    }
                    if(c.getEntity() instanceof ExtraLife){
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(null);
                        collectedExtra.add(new Position(playerRow,playerCol));
                    }
                    if(c.getEntity() instanceof Mine){
                        collectedExtra.clear();
                        collectedGems.clear();
                        return new MoveResult.Valid.Dead(playerPosition, playerPosition.offsetBy(0, delta * offsetC));
                    }else if (c instanceof StopCell){
                        //gameBoard.getEntityCell(playerRow, playerCol).setEntity(gameBoard.getPlayer());
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(0, delta * offsetC), playerPosition,collectedGems,collectedExtra);
                    }

                }
                if ((playerCol + offsetC) < 0 || (playerCol + offsetC)>=gameBoard.getNumCols()){
                    //System.out.println("HIHIHIHIHI");
                    //gameBoard.getEntityCell(playerRow, playerCol).setEntity(gameBoard.getPlayer());
                    return new MoveResult.Valid.Alive(playerPosition.offsetBy(0, delta * offsetC), playerPosition,collectedGems,collectedExtra);
                }
                delta++;
                playerCol += offsetC;
            }
        }
        return new MoveResult.Invalid(playerPosition);
    }

    /**
     * Undoes a move by reverting all changes performed by the specified move.
     *
     * <p>
     * Hint: Undoing a move is effectively the same as reversing everything you have done to make a move.
     * </p>
     *
     * @param prevMove The {@link MoveResult} object to revert.
     */
    public void undoMove(@NotNull final MoveResult prevMove) {
        // TODO(DONE)
        if (prevMove instanceof MoveResult.Valid c){
            gameBoard.getEntityCell(c.origPosition).setEntity(gameBoard.getEntityCell(c.newPosition).getEntity());
            if (c instanceof MoveResult.Valid.Alive d){
                if (!d.collectedGems.isEmpty()){
                    for(int i = 0; i < d.collectedGems.size(); i++){
                        gameBoard.getEntityCell(d.collectedGems.get(i)).setEntity(new Gem());
                    }
                }
                if (!d.collectedExtraLives.isEmpty()){
                    for(int i = 0; i < d.collectedExtraLives.size(); i++){
                        gameBoard.getEntityCell(d.collectedExtraLives.get(i)).setEntity(new ExtraLife());

                    }
                }
            }
        }
    }
}
