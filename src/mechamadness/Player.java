/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Player class. All the attributes of the player as far as the server is
 * concerned. Recieves events grom the Game. Portability to the Client side will
 * probably be useful, but we'll see.
 *
 * @author pwallington
 */
public class Player {

    private int playerId;
    private int livesLeft;
    private int damage;
    private Card[] registers;
    private Card[] hand;
    private boolean[] registerLocked;
    private Position position;
    private Direction orientation;
    private Position checkpoint;
    // FIXME erm. REALLY? only needed to fill empty registers as they get locked
    private Deck gameDeck;

    public Position getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Position checkpoint) {
        this.checkpoint = checkpoint;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
        if (this.damage > 5) {
            for (int i = this.damage - 5;;) {
                //FIXME NOT FINISHED - Should this method do lock/unlock actions?
            }
        }
    }

    public void addDamage(int damage) {
        if (damage <= 0 || damage > 10) {
            throw new java.lang.UnsupportedOperationException("Invalid addDamage value " + damage);
        }

        for (int i = this.damage; i < Math.min(this.damage + damage, 9); i++) {
            if (i > 4) {
                /* Register number (9 - n) gets locked */
                this.setRegisterLockStatus(9 - i, java.lang.Boolean.TRUE);
            }
        }
        this.damage = Math.min(this.damage + damage, 10);
        if (this.damage == 10) {
            // FIXME Oh. We died. What now? return(O_NOES_I_R_DEAD) ?
        }
    }

    public void healDamage(int damage) {
        if (damage <= 0 || damage > 10) {
            throw new java.lang.UnsupportedOperationException("Invalid healDamage value " + damage);
        }

        for (int i = this.damage; i < Math.max(this.damage - damage, 0); i--) {
            if (i > 4) {
                /* Register number (9 - n) gets unlocked */
                this.setRegisterLockStatus(9 - i, java.lang.Boolean.FALSE);
            }
        }
        this.damage = Math.max(this.damage - damage, 0);
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
        for (int i = 0; i < this.hand.length; i++) {
            this.hand[i].setWithPlayer(this.playerId);
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public Position getPosition() {
        return position;
    }

    public boolean getRegisterLockStatus(int index) {
        return registerLocked[index];
    }

    /**
     * Lock or unlock a register, by its index. If the register is empty then
     *  a card is dealt off the deck to fill it. Sets the lockedInRegister
     *  field in the card that's locked into the register.
     * @param index The register to be locked or unlocked.
     * @param status The new lock status - TRUE == locked
     */
    public void setRegisterLockStatus(int index, boolean status) {

        this.registerLocked[index] = status;

        if (status == java.lang.Boolean.TRUE) {
            /* locking a register */
            if (this.registers[index] == null) {
                /* The register is empty, so get a card to fill it */
                this.registers[index] = this.gameDeck.dealCard();
            }

            this.registers[index].setLockedInRegister(index);

        } else {
            /* unlocking a register */
            this.registers[index].setLockedInRegister(-1);
        }
    }

    public Card getRegister(int index) {
        return registers[index];
    }

    /**
     * Assign a specific card to a register.
     * @param register The register to place the card in. An UnsupportedOperationException
     *                 will be thrown if the register already has a card in it.
     * @param cardIndex The Deck index of the card to place in the register.
     *                  An UnsupportedOperationException will be thrown if the
     *                  card is not in the player's hand
     */
    public void assignCard(int register, int cardIndex) {
        if (this.registers[register] != null) {
            throw new java.lang.UnsupportedOperationException("Register already filled!");
        }

        if (this.registerLocked[register]) {
            throw new java.lang.UnsupportedOperationException("Register is locked!");
        }
        for (int i = 0; i < this.hand.length; i++) {
            if ((this.hand[i] != null) && (this.hand[i].getIndex() == cardIndex)) {
                this.registers[register] = this.hand[i];
                this.hand[i] = null;
                return;
            }
        }
        /* Fell out of loop, card didn't match any in the hand */
        throw new java.lang.UnsupportedOperationException("Card not in hand!");
    }

    /**
     * Assign a random card from the player's hand to the specified register.
     * @param register The register to place the card in. An UnsupportedOperationException
     *                 will be thrown if the register already has a card in it.
     */
    public void assignRandomCard(int register) {
        int i;

        if (this.registers[register] != null) {
            throw new java.lang.UnsupportedOperationException("Register already filled!");
        }

        if (this.registerLocked[register]) {
            throw new java.lang.UnsupportedOperationException("Register is locked!");
        }

        do {
            i = (int) Math.floor(Math.random() * this.hand.length);
        } while (this.hand[i] == null);

        this.registers[register] = this.hand[i];
        this.hand[i] = null;
    }

    /**
     * Get the Player to give up ownership of their Cards. Sets all cards as
     *  being with player '-1', and forgets all the Card references in the hand
     *  and the registers (unless the register is locked!)
     */
    public void retrieveCards() {
        for (int i = 0; i < this.registers.length; i++) {
            if (this.getRegisterLockStatus(i) == java.lang.Boolean.FALSE) {
                this.registers[i].setWithPlayer(-1);
                this.registers[i] = null;
            }
        }

        for (int i = 0; i < this.hand.length; i++) {
            this.hand[i].setWithPlayer(-1);
            this.hand[i] = null;
        }
    }

    /**
     * Move the Player in the direction specified. Recursively moves Players in
     * the destination square.
     * @param direction Direction of the move
     * @return TRUE or FALSE depending on whether the move was successful (used by recursion)
     */
    public boolean move(Direction direction) {
        Board board = Board.getInstance();
        Player playerRef;

        /* Is it valid to move in this direction? (walls etc) */
        if (board.isMoveValid(this.position, direction) == java.lang.Boolean.FALSE) {
            return (java.lang.Boolean.FALSE);
        } else {
            /* Is there a robot on that square? */
            playerRef = board.getPositionOccupier(this.position.neighbour(direction));
            /* Move that robot, same direction */
            if (playerRef != null) {
                if (playerRef.move(direction) == java.lang.Boolean.FALSE) {
                    return (java.lang.Boolean.FALSE);
                }
            }
        }
        this.updatePosition(direction);
        return (java.lang.Boolean.TRUE);
    }

    public void updatePosition(Direction direction) {
        switch(direction)
        {
            case UP:
                this.position.Y++;
                break;
            case DOWN:
                this.position.Y--;
                break;
            case LEFT:
                this.position.X--;
                break;
            case RIGHT:
                this.position.X++;
                break;
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean doCardMove(Move move)
    {
        if ((move.forward == 0) && (move.turn == null)) {
            throw new UnsupportedOperationException("Invalid Move parameters!");
        }

        if (move.forward == 0) {
            /* Rotate */
            //FIXME break rotation code out into a new function
            switch (move.turn) {
                case LEFT:
                    switch(this.orientation) {
                        case UP:
                            this.orientation = Direction.LEFT;
                            break;
                        case LEFT:
                            this.orientation = Direction.DOWN;
                            break;
                        case DOWN:
                            this.orientation = Direction.RIGHT;
                            break;
                        case RIGHT:
                            this.orientation = Direction.UP;
                            break;
                    }
                    break;
                case RIGHT:
                    switch(this.orientation) {
                        case UP:
                            this.orientation = Direction.RIGHT;
                            break;
                        case LEFT:
                            this.orientation = Direction.UP;
                            break;
                        case DOWN:
                            this.orientation = Direction.LEFT;
                            break;
                        case RIGHT:
                            this.orientation = Direction.DOWN;
                            break;
                    }
                    break;
            }
            return (java.lang.Boolean.TRUE);
        } else if (move.forward == -1) {
            /* Move backwards */
            switch (this.orientation) {
                case UP:    return (this.move(Direction.DOWN));
                case DOWN:  return (this.move(Direction.UP));
                case LEFT:  return (this.move(Direction.RIGHT));
                case RIGHT: return (this.move(Direction.LEFT));
            }
        } else {
            /* Do N moves */
            for (int i = 0; i < move.forward; i++) {
                if (this.move(this.orientation) == java.lang.Boolean.FALSE) {
                    return (java.lang.Boolean.FALSE);
                }
            }
        }
        return (java.lang.Boolean.TRUE);
    }
}