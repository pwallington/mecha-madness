/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Board class. I blame the teacher.
 * @author pwallington
 */
class Board {

    static Board boardref;
    private int maxPlayers = 8;
    private int numPlayers = 0;
    private Layout layout;
    private Player[] playerlist = new Player[maxPlayers];

    static Board getInstance() {
        if (boardref == null) {
            //FIXME sort out constructor params.
            boardref = new Board();
        }
        return (boardref);
    }

    public boolean isMoveValid(Position pos, Direction direction) {
        //FIXME implement 'isMoveValid'
        return this.layout.isMoveValid(pos, direction);
    }

    /**
     * Find the occupier of a board position for collision detection.
     * @param pos - position to check.
     * @return Player ref for occupier of pos. Null if none found.
     */
    public Player getPositionOccupier(Position pos) {
        for (int i = 0; i < maxPlayers; i++) {
            if (this.playerlist[i] != null) {
                if (playerlist[i].getPosition().equals(pos)) {
                    return playerlist[i];
                }
            }
        }
        /* If it fell out of the loop then no Players were found there
         * So we return null
         */
        return null;
    }

    void setLayout(Layout layout) {
        this.layout = layout;
        if (this.layout.getMaxPlayers() != 0) {
            this.maxPlayers = this.layout.getMaxPlayers();
        }
    }

    public boolean addPlayer(Player newPlayer) {
        //FIXME implement 'addPlayer'
        if (numPlayers >= maxPlayers) {
            throw new UnsupportedOperationException("Player limimt reached") ;
        }
        playerlist[numPlayers++] = newPlayer;

        throw new UnsupportedOperationException("addPlayer() Not yet implemented");
    }
}
