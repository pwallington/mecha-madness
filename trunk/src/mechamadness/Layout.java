/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

import java.awt.Rectangle;

/**
 * Clas to handle the layout of the board.
 * @todo Implement layout constructor, loading, saving, etc.
 * @author pwallington
 */
public class Layout {

    private Rectangle boardSize;
    private int maxPlayers;
    private LayoutSquare grid[][];
    private Position startPos[] = new Position[5];

    public Rectangle getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Rectangle boardSize) {
        this.boardSize = boardSize;
    }

    private void setBoardSize(int minX, int maxX, int minY, int maxY) {
        this.boardSize = new Rectangle(minX, maxX, minY, maxY);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    private void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Work out if a robot can move in a particular direction
     * from a given position - i.e. wall detection.
     * @todo Catch cases where the given position is not on the 'grid'
     * @param pos
     * @param dir
     * @return
     */
    public boolean isMoveValid(Position pos, Direction dir) {
        LayoutSquare square = this.grid[pos.X][pos.Y];
        return (square.walls[Direction.toValue(dir)]);
    }

    /**
     * helper class for a single square on the grid.
     */
    private class LayoutSquare {

        public Position pos;
        public SquareType type;
        public boolean walls[] = new boolean[4];
        public Direction dir;
    }
}

