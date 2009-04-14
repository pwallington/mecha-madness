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

    public Move(int forward) {
        this.forward = forward;
        this.turn = Direction.UP;
    }

    public Move(Direction turn) {
        this.forward = 0;

        if (turn == Direction.DOWN) {
            this.turn = Direction.UP;
        } else {
            this.turn = turn;
        }
    }

    public Move(int forward, Direction turn) {
        this.forward = forward;
        this.turn = turn;
    }
}
