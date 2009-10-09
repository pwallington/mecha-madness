/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Helper Class to provide a Move object that can be passed around easily.
 * @param forward <code>int</code> Forward movement value.
 * @param turn <code>Direction.LEFT</code> or <code>Direction.RIGHT</code> for turn cards.
 * @author pwallington
 */
public class Move {

    public int forward;
    public Direction turn;

    /**
     * Constructor to create a forward move.
     * @param forward - spaces forward to move. -1 == 'Back-up'
     */
    public Move(int forward) {
        this.forward = forward;
        this.turn = Direction.UP;
    }

    /**
     * Constructor to create a rotation
     * @param turn - direction to rotate. DOWN == 'U-Turn'
     */
    public Move(Direction turn) {
        this.forward = 0;
        this.turn = turn;
    }

    /**
     * Move & Rotate. E.g. conveyor belt 'corners'
     * @param forward
     * @param turn
     */
    public Move(int forward, Direction turn) {
        this.forward = forward;
        this.turn = turn;
    }
}
