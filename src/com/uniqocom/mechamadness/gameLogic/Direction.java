/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniqocom.mechamadness.gameLogic;

/**
 * General enum for directions on the board.
 * <p> BOTTOM LEFT of the board == (0,0)
 * <p> Thus UP = +y, DOWN = -y, RIGHT = +x, LEFT = -x
 * For array indices, UP = 0, proceeding clockwise.
 * @author pwallington
 */
public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    /**
     * Convert Direction to int
     * @param dir
     * @return int
     */
    public static int toValue(Direction dir) {
        int dirNum;
        switch (dir) {
            case UP:
                dirNum = 0;
            case RIGHT:
                dirNum = 1;
            case DOWN:
                dirNum = 2;
            case LEFT:
                dirNum = 3;
            default:
                dirNum = -1;
        }
        return (dirNum);
    }

    /**
     * Convert int to Direction
     * @param i
     * @return Direction
     */
    public static Direction toDir(int i) {
        switch (i) {
            case 0:
                return UP;
            case 1:
                return RIGHT;
            case 2:
                return DOWN;
            case 3:
                return LEFT;
            default:
                return null;
        }
    }
}
