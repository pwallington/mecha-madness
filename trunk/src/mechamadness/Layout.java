/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

import java.awt.Rectangle;

/**
 * Class to handle the layout of the board.
 * @todo Implement layout constructor, loading, saving, etc.
 * @author pwallington
 */
public class Layout {

    private Rectangle boardSize;
    private int maxPlayers;
    private LayoutSquare grid[][];
    private Position startPos[];

    /**
     * Debug constructor to initialise stuff.
     * @todo REPLACE ME
     */
    Layout(int sizeX, int sizeY) {
        this.boardSize.setLocation(0, 0);
        this.boardSize.setSize(sizeX, sizeY);
        
        grid = new LayoutSquare[sizeX][sizeY];
        
        for (int x = 0; x < sizeX; x++)
        {
            for (int y = 0; y < sizeY; y++)
            {
                LayoutSquare sq = grid[x][y];
                sq.dir = Direction.UP;
                sq.pos = new Position(x, y);
                sq.walls[0] = false;
                sq.walls[1] = false;
                sq.walls[2] = false;
                sq.walls[3] = false;
                sq.type = SquareType.BLANK;
            }
        }
        maxPlayers = 8;
        startPos = new Position[8];

        startPos[0] = new Position(0,0);
        startPos[1] = new Position(2,0);
        startPos[2] = new Position(4,0);
        startPos[3] = new Position(6,0);
        startPos[4] = new Position(8,0);
        startPos[5] = new Position(10,0);
        startPos[6] = new Position(12,0);
        startPos[7] = new Position(14,0);
    }

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

    public Position getStartPos(int index) {
        return startPos[index];
    }

    /**
     * Work out if a robot can move in a particular direction
     * from a given position.
     * @throws ArrayIndexOutOfBoundsException if pos is not on the board.
     * @param pos position to check from
     * @param dir direction to check
     * @return TRUE if move is allowed, FALSE otherwise.
     */
    public boolean isMoveValid(Position pos, Direction dir) {
        LayoutSquare square = this.grid[pos.X][pos.Y];
        /*
         * If this square has a wall in that dir, the move isn't valid.
         */
        return (!square.walls[Direction.toValue(dir)]);
    }

    /**
     * helper class for a single square on the grid.
     */
    private class LayoutSquare {

        public LayoutSquare(Position pos, SquareType type, Direction dir) {
            this.pos = pos;
            this.type = type;
            this.dir = dir;
        }

        /**
         * Position on the layout
         * FIXME - pos may not be neccessary?
         */
        public Position pos;
        /**
         * Squaretype - enum.
         */
        public SquareType type;
        /**
         * Walls around this square. Indexed with Direction.toValue()
         */
        public boolean walls[] = new boolean[4];
        /**
         * Direction, for pushers, conveyors etc.
         */
        public Direction dir;
    }
}

