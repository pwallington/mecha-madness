/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniqocom.mechamadness.gameLogic;

/**
 * Board class. I blame the teacher.
 * @author pwallington
 */
class Board {

    public Board(BoardLayout layout) {
        boardref = this;
        this.layout = layout;
    }

    static Board boardref;
    private int maxPlayers = 8;
    private int numPlayers = 0;
    private BoardLayout layout;
    private Player[] playerlist = new Player[maxPlayers];

    static Board getInstance() {
        if (boardref == null) {
            //FIXME sort out constructor params.
            boardref = new Board(null);
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

    void setLayout(BoardLayout layout) {
        this.layout = layout;
        if (this.layout.getMaxPlayers() != 0) {
            this.maxPlayers = this.layout.getMaxPlayers();
        }
    }

    public boolean addPlayer(Player newPlayer) {
        //FIXME implement 'addPlayer'
        if (numPlayers >= maxPlayers) {
            throw new UnsupportedOperationException("Player limit already reached") ;
        }

        int playerIndex = numPlayers++;
        playerlist[playerIndex] = newPlayer;

        newPlayer.setPosition(layout.getStartPos(playerIndex));

        throw new UnsupportedOperationException("FIXME addPlayer() Not finished!");
    }
}
