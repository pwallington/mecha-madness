/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mechamadness;

/**
 * Helper Class to provide a Move object that can be passed around easily.
 * @param forward <code>int</code> Forward movement value.
 * @param rotation <code>int</code> Rotation in 90degree steps TO THE RIGHT.
 * @author pwallington
 */
public class Move {
    public int forward;
    public int turn;

    public Move(int forward, int turn) {
        this.forward = forward;
        this.turn = turn;
    }

}
