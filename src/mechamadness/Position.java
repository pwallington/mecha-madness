/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Helper class to describe positions on the board.
 * @author pwallington
 */
public class Position {

    public int X;
    public int Y;

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Constructs a Position object for a neighbouring space.
     * @todo Handle board edges. Maybe.
     * @param direction - Direction enum for relative position
     * @return Position oject neighboring this Position
     */
    Position neighbour(Direction direction) {
        int neigbourX = this.X;
        int neigbourY = this.Y;

        switch (direction) {
            case LEFT:
                neigbourX--;
            case RIGHT:
                neigbourX++;
            case UP:
                neigbourY++;
            case DOWN:
                neigbourY--;
        }
        return (new Position(neigbourX, neigbourY));
    }

    /**
     * Really simple equality check.
     * @param pos
     * @return
     */
    boolean equals(Position pos)
    {
        if ((this.X == pos.X) && (this.Y == pos.Y))
            return (true);
        else
            return (false);
    }
}
