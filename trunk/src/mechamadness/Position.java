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

    Position neighbour(Direction direction) {
        //FIXME implement 'neighbour'
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
