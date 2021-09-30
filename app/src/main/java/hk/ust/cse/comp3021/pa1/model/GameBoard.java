package hk.ust.cse.comp3021.pa1.model;

import org.jetbrains.annotations.NotNull;

/**
 * The main game board of the game.
 *
 * <p>
 * The top-left hand corner of the game board is the "origin" of the board (0, 0).
 * </p>
 */
public final class GameBoard {

    /**
     * Number of rows in the game board.
     */
    private final int numRows;
    /**
     * Number of columns in the game board.
     */
    private final int numCols;

    /**
     * 2D array representing each cell in the game board.
     */
    @NotNull
    private final Cell[][] board;

    /**
     * The instance of {@link Player} on this game board.
     */
    @NotNull
    private final Player player;

    /**
     * Creates an instance using the provided creation parameters.
     *
     * @param numRows The number of rows in the game board.
     * @param numCols The number of columns in the game board.
     * @param cells   The initial values of cells.
     * @throws IllegalArgumentException if any of the following are true:
     *                                  <ul>
     *                                      <li>{@code numRows} is not equal to {@code cells.length}</li>
     *                                      <li>{@code numCols} is not equal to {@code cells[0].length}</li>
     *                                      <li>There is no player or more than one player in {@code cells}</li>
     *                                      <li>There are no gems in {@code cells}</li>
     *                                      <li>There are some gems which cannot be reached by the player</li>
     *                                  </ul>
     */
    public GameBoard(final int numRows, final int numCols, @NotNull final Cell[][] cells) {
        // TODO(DONE)
        if (cells.length != numRows) {
            throw new IllegalArgumentException("cell's row is not the same as the number of rows entered");
        }
        this.numRows = numRows;

        if (cells[0].length != numCols) {
            throw new IllegalArgumentException("cell's column is not the same as the number of column entered");
        }
        this.numCols = numCols;

        int numPlayer = 0;
        int xPlayer = 0;
        int yPlayer = 0;
        Player onlyPlayer = null;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (cells[i][j] instanceof EntityCell c) {
                    if (c.entity instanceof Player) {
                        onlyPlayer = (Player) c.entity;
                        xPlayer = j;
                        yPlayer = i;
                        numPlayer++;
                    }
                }
            }
            if (numPlayer > 1) {
                throw new IllegalArgumentException("should not have more than one player");
            }
        }
        if (numPlayer == 0) {
            throw new IllegalArgumentException("should not have more than one player");
        }
        this.player = onlyPlayer;

        int numGem = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (cells[i][j] instanceof EntityCell c) {
                    if (c.entity instanceof Gem) {
                        numGem++;
                    }
                }
            }
        }
        Integer numReachableGem = 0;
        boolean[][][] fill = new boolean[numRows][numCols][2];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                fill[i][j][0] = false;
                fill[i][j][1] = false;
            }
        }
        if(numGem == 0){
            throw new IllegalArgumentException("there is no gem at all");
        }else if (numGem == reachableCell(xPlayer, yPlayer, cells, numReachableGem, fill)){
            this.board = cells;
        }
        else{
            throw new IllegalArgumentException("not All gems are reachable");
        }
    }

    private int reachableCell(int x, int y, @NotNull final Cell[][] cells, Integer numGems, boolean[][][] fill){
        fill[y][x][0] = true;
        for (int i = y - 1; i >= 0; i--){  //up
            if (i == 0){
                if (!fill[i][x][0]){
                    reachableCell(x, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][x] instanceof Wall){
                if(i == y - 1){
                    break;
                }else if (!fill[i+1][x][0]){
                    reachableCell(x, i+1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][x] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][x][1]){
                        numGems++;
                        fill[i][x][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][x][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][x][0]){
                        reachableCell(x, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = y + 1; i <numRows; i++){ // down
            if (i+1 == numRows){
                if (!fill[i][x][0]){
                    reachableCell(x, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][x] instanceof Wall){
                if(i == y +1){
                    break;
                }else if (!fill[i-1][x][0]){
                    reachableCell(x, i-1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][x] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][x][1]){
                        numGems++;
                        fill[i][x][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][x][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][x][0]){
                        reachableCell(x, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = x - 1; i >= 0; i--){ //left
            if (i == 0){
                if (!fill[y][i][0]){
                    reachableCell(i, y, cells, numGems, fill);
                    break;
                }
            }
            if (cells[y][i] instanceof Wall){
                if(i == x - 1){
                    break;
                }else if (!fill[y][i + 1][0]){
                    reachableCell(i+1, y, cells, numGems, fill);
                    break;
                }
            }
            if (cells[y][i] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[y][i][1]){
                        numGems++;
                        fill[y][i][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[y][i][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[y][i][0]){
                        reachableCell(i, y, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = x + 1; i <numCols; i++){ //right
            if (i+1 == numCols){
                if (!fill[y][i][0]){
                    reachableCell(i, y, cells, numGems, fill);
                    break;
                }
            }
            if (cells[y][i] instanceof Wall){
                if(i == x +1){
                    break;
                }else if (!fill[y][i - 1][0]){
                    reachableCell(i - 1, y, cells, numGems, fill);
                    break;
                }
            }
            if (cells[y][i] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[y][i][1]){
                        numGems++;
                        fill[y][i][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[y][i][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[y][i][0]){
                        reachableCell(i, y, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = y - 1, j = x - 1; i >= 0 || j >= 0; i--, j--){ //up left
            if (i == 0 || j == 0){
                if (!fill[i][j][0]){
                    reachableCell(j, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof Wall){
                if(i == y - 1 && j == x - 1){
                    break;
                }else if (!fill[i+1][j+1][0]){
                    reachableCell(j+1, i+1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][j][1]){
                        numGems++;
                        fill[i][j][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][j][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][j][0]){
                        reachableCell(j, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = y - 1, j = x + 1; i >= 0 || j <numCols; i--, j++){ // up right
            if (i == 0 || j + 1 == numCols){
                if (!fill[i][j][0]){
                    reachableCell(j, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof Wall){
                if(i == y - 1 && j == x + 1){
                    break;
                }else if (!fill[i+1][j-1][0]){
                    reachableCell(j-1, i+1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][j][1]){
                        numGems++;
                        fill[i][j][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][j][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][j][0]){
                        reachableCell(j, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = y + 1, j = x - 1; i < numRows || j >= 0; i++, j--){ // down left
            if (i +1 == numRows || j == 0){
                if (!fill[i][j][0]){
                    reachableCell(j, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof Wall){
                if(i == y + 1 && j == x - 1){
                    break;
                }else if (!fill[i-1][j+1][0]){
                    reachableCell(j+1, i-1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][j][1]){
                        numGems++;
                        fill[i][j][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][j][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][j][0]){
                        reachableCell(j, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        for (int i = y + 1, j = x + 1; i < numRows || j < numCols; i++, j++){ // down right
            if (i +1 == numRows || j +1 == numCols){
                if (!fill[i][j][0]){
                    reachableCell(j, i, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof Wall){
                if(i == y + 1 && j == x + 1){
                    break;
                }else if (!fill[i-1][j-1][0]){
                    reachableCell(j-1, i-1, cells, numGems, fill);
                    break;
                }
            }
            if (cells[i][j] instanceof EntityCell c){
                if(c.entity instanceof Gem){
                    if (!fill[i][j][1]){
                        numGems++;
                        fill[i][j][1] = true;
                    }
                }else if (c.entity instanceof Mine){
                    fill[i][j][0] = true;
                    break;
                }
                if(c instanceof StopCell ){
                    if (!fill[i][j][0]){
                        reachableCell(j, i, cells, numGems, fill);
                        break;
                    }
                }
            }
        }
        return numGems;
    }

    /**
     * Returns the {@link Cell}s of a single row of the game board.
     *
     * @param r Row index.
     * @return 1D array representing the row. The first element in the array corresponds to the leftmost element of the
     * row.
     */
    @NotNull
    public Cell[] getRow(final int r) {
        // TODO(DONE)
        return board[r];
    }

    /**
     * Returns the {@link Cell}s of a single column of the game board.
     *
     * @param c Column index.
     * @return 1D array representing the column. The first element in the array corresponds to the topmost element of
     * the row.
     */
    @NotNull
    public Cell[] getCol(final int c) {
        // TODO(DONE)
        Cell[] selectedColumn = new Cell[numCols];
        for (int i = 0; i< numCols; i++){
            selectedColumn[i] = board[i][c];
        }
        return selectedColumn;
    }

    /**
     * Returns a single cell of the game board.
     *
     * @param r Row index.
     * @param c Column index.
     * @return The {@link Cell} instance at the specified location.
     */
    @NotNull
    public Cell getCell(final int r, final int c) {
        // TODO(DONE)
        return board[r][c];
    }

    /**
     * Returns a single cell of the game board.
     *
     * @param position The position object representing the location of the cell.
     * @return The {@link Cell} instance at the specified location.
     */
    @NotNull
    public Cell getCell(@NotNull final Position position) {
        // TODO(DONE)
        return board[position.row()][position.col()];
    }

    /**
     * Returns an {@link EntityCell} on the game board.
     *
     * <p>
     * This method is a convenience method for getting a cell which is unconditionally known to be an entity cell.
     * </p>
     *
     * @param r Row index.
     * @param c Column index.
     * @return The {@link EntityCell} instance at the specified location.
     * @throws IllegalArgumentException if the cell at the specified position is not an instance of {@link EntityCell}.
     */
    @NotNull
    public EntityCell getEntityCell(final int r, final int c) {
        // TODO(DONE)
        if (board[r][c] instanceof EntityCell d){
            return d;
        }
        else{
            throw new IllegalArgumentException("Cell not an EntityCell");
        }
    }

    /**
     * Returns an {@link EntityCell} on the game board.
     *
     * <p>
     * This method is a convenience method for getting a cell which is unconditionally known to be an entity cell.
     * </p>
     *
     * @param position The position object representing the location of the cell.
     * @return The {@link EntityCell} instance at the specified location.
     * @throws IllegalArgumentException if the cell at the specified position is not an instance of {@link EntityCell}.
     */
    @NotNull
    public EntityCell getEntityCell(@NotNull final Position position) {
        // TODO(DONE)
        if (board[position.row()][position.col()] instanceof EntityCell d) {
            return d;
        } else {
            throw new IllegalArgumentException("Cell not an EntityCell");
        }
    }

    /**
     * @return The number of rows of this game board.
     */
    public int getNumRows() {
        // TODO(DONE)
        return numRows;
    }

    /**
     * @return The number of columns of this game board.
     */
    public int getNumCols() {
        // TODO(DONE)
        return numCols;
    }

    /**
     * @return The player instance.
     */
    @NotNull
    public Player getPlayer() {
        // TODO(DONE)
        return player;
    }

    /**
     * @return The number of gems still present in the game board.
     */
    public int getNumGems() {
        // TODO(DONE)
        int numGem = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (board[i][j] instanceof EntityCell c) {
                    if (c.entity instanceof Gem) {
                        numGem++;
                    }
                }
            }
        }
        return numGem;
    }
}
