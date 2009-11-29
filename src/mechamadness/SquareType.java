/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mechamadness;

/**
 * SquareType enum. Used for determining the type of a board square.
 * @author pwallington
 */
public enum SquareType {
    /**
     * Plain tile, nothing to see here.
     */
    BLANK,

    /**
     * Pit. Self-explanatory. Also used for 'off-board' tiles.
     */
    PIT,

    /**
     * Rotating gear.
     * Uses Direction LEFT for anti-clockwise, RIGHT for clockwise
     * i.e. the direction in whih the robot is rotated by the gear.
     */
    GEAR,

    /**
     * Standard conveyor tile.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR,

    /**
     * Standard conveyor left-hand corner tile.
     * Rotates a robot LEFT when moved onto by a conveyor.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR_CORNER_LEFT,

    /**
     * Standard conveyor right-hand corner tile.
     * Rotates a robot RIGHT when moved onto by a conveyor.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR_CORNER_RIGHT,

    /**
     * Standard conveyor left-hand joining tile.
     * Rotates a robot LEFT when moved onto by a conveyor from the 'spur'.
     * i.e. if the robot's movement direction DOES NOT match the tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR_JOIN_LEFT,

    /**
     * Standard conveyor right-hand joining tile.
     * Rotates a robot RIGHT when moved onto by a conveyor from the 'spur'.
     * i.e. if the robot's movement direction DOES NOT match the tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR_JOIN_RIGHT,
    
    /**
     * Standard conveyor T-Junciton tile.
     * Rotates a robot LEFT when moved onto by a conveyor from the LEFT of the
     * tile dir.
     * Rotates a robot RIGHT when moved onto by a conveyr from the RIGHT of the 
     * tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    CONVEYOR_TEE,

    /**
     * Express conveyor tile.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS,

    /**
     * Express conveyor left-hand corner tile.
     * Rotates a robot LEFT when moved onto by a conveyor.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS_CORNER_LEFT,

    /**
     * Express conveyor right-hand corner tile.
     * Rotates a robot RIGHT when moved onto by a conveyor.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS_CORNER_RIGHT,

    /**
     * Express conveyor left-hand joining tile.
     * Rotates a robot LEFT when moved onto by a conveyor from the 'spur'.
     * i.e. if the robot's movement direction DOES NOT match the tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS_JOIN_LEFT,

    /**
     * Express conveyor left-hand joining tile.
     * Rotates a robot LEFT when moved onto by a conveyor from the 'spur'.
     * i.e. if the robot's movement direction DOES NOT match the tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS_JOIN_RIGHT,

    /**
     * Express conveyor T-Junciton tile.
     * Rotates a robot LEFT when moved onto by a conveyor from the LEFT of the
     * tile dir.
     * Rotates a robot RIGHT when moved onto by a conveyor from the RIGHT of the
     * tile dir.
     * Used with direction, which indicates 'exit' dir.
     */
    EXPRESS_TEE,

    /**
     * Pusher tile.
     * Pushes a robot OFF this tile, in the direction given by the tile dir.
     * Only pushes on even-numbered phases.
     */
    PUSHEREVEN,

    /**
     * Pusher tile.
     * Pushes a robot OFF this tile, in the direction given by the tile dir.
     * Only pushes on odd-numbered phases.
     */
    PUSHERODD;
}
