package sk.tsystems.gamestudio.game.minesweeper.core;
import java.util.Random;

//import  sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    private long startMillis;
    
    
    
    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
        startMillis = System.currentTimeMillis();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
        	tiles[row][column].setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }
            
            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
            
            
            if (tile instanceof Clue) {
				if (((Clue) tile).getValue() == 0)
					openAdjacentTiles(row, column);
			}
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		
		if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.MARKED);
        }
		else if(tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}

    	
    	
      //  throw new UnsupportedOperationException("Method markTile not yet implemented");
    }

    /**
     * Generates playing field.
     */
    private void generate() {
    	Random r = new Random();
    	 int counter = 0;
    	 
    	 while(counter < mineCount) {
    		 	int randomRowCount = (r.nextInt(rowCount) );
    	        int randomColumnCount = (r.nextInt(columnCount));
    	        
    	        if(tiles[randomRowCount][randomColumnCount] == null) {
    	        	tiles[randomRowCount][randomColumnCount] = new Mine();
    	        	counter++;
    	        }}
    	        
    	        for (int i = 0; i < rowCount; i++) {
    	        	for (int j = 0; j < columnCount; j++) {
						if(tiles[i][j] == null) {
							tiles[i][j] = new Clue(countAdjacentMines(i,j));
						}
						
						}
					}
					
				}    

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
    	
   return  (rowCount*columnCount) - getNumberOf(Tile.State.OPEN) == mineCount;
    	
    }
    
    
    

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}
	
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	private int getNumberOf(Tile.State state) {
		int counter = 0;
    	for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if(tiles[i][j].getState() == state ) {
					++counter;
				}
			}
		}
    	
		return counter;
	}
	
	private void openAdjacentTiles(int row, int column){
    	for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                    	openTile(actRow, actColumn);
                    }
                }
            }
        }
    }
	
	
	public int getMinesScores() {
		
		if(state == GameState.SOLVED) {
            int seconds = (int)((System.currentTimeMillis() - startMillis) / 1000);
            
            int score = (rowCount * columnCount * 4) - seconds;
            
            return score;
        }
        return 0;
    }

			
}
