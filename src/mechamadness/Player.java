/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Player class. All the attributes of the player as far as the server is
 * concerned. Recieves events grom the Game. Portability to the Client side will
 * probably be useful, but we'll see.
 * @todo constructor, get singleton refs. dying/respawning. setPosition()
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
    // FIXME erm, do we need a Deck reference? REALLY? only needed to fill empty registers as they get locked
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
        if (damage <= 0 || damage > 10) {
            throw new UnsupportedOperationException("Invalid setDamage value: " + damage);
        }

        this.damage = damage;

        /* 
         * Unlock registers
         */
        for (int i = 0; (i < (9 - this.damage)) && (i < 5); i++) {
            this.setRegisterLockStatus(i, false);
        }

        /*
         * Lock registers
         */
        for (int i = 9 - this.damage; i < 5; i++) {
            this.setRegisterLockStatus(i, true);
        }

    }

    public void addDamage(int damage) {
        if (damage <= 0 || damage > 10) {
            throw new UnsupportedOperationException("Invalid addDamage value: " + damage);
        }

        for (int i = this.damage; i <
                Math.min(this.damage + damage, 9); i++) {
            if (i > 4) {
                /* Register number (9 - n) gets locked */
                this.setRegisterLockStatus(9 - i, true);
            }

        }
        this.damage = Math.min(this.damage + damage, 10);
        if (this.damage == 10) {
            // FIXME Oh. We died. What now? return(O_NOES_I_R_DEAD) ?
            throw new UnsupportedOperationException("Dead!");
        }

    }

    public void healDamage(int damage) {
        if (damage <= 0 || damage > 10) {
            throw new UnsupportedOperationException("Invalid healDamage value: " + damage);
        }

        for (int i = this.damage; i <
                Math.max(this.damage - damage, 0); i--) {
            if (i > 4) {
                /* Register number (9 - n) gets unlocked */
                this.setRegisterLockStatus(9 - i, false);
            }

        }
        this.damage = Math.max(this.damage - damage, 0);
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
        for (int i = 0; i <
                this.hand.length; i++) {
            this.hand[i].setWithPlayer(this.playerId);
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPosition(Position position) {
        this.position = position;
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

        if (status == true) {
            /* locking a register */
            if (this.registers[index] == null) {
                /* The register is empty, so get a card to fill it */
                this.registers[index] = this.gameDeck.dealCard();
                throw new UnsupportedOperationException("Filling empty registers not implemented yet!");
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
            throw new UnsupportedOperationException("Register already filled!");
        }

        if (this.registerLocked[register]) {
            throw new UnsupportedOperationException("Register is locked!");
        }

        for (int i = 0; i <this.hand.length; i++) {
            if ((this.hand[i] != null) && (this.hand[i].getIndex() == cardIndex)) {
                this.registers[register] = this.hand[i];
                this.hand[i] = null;
                return;
            }
        }
        /* Fell out of loop, card didn't match any in the hand */
        throw new UnsupportedOperationException("Card not in hand!");
    }

    /**
     * Assign a random card from the player's hand to the specified register.
     * @param register The register to place the card in. An UnsupportedOperationException
     *                 will be thrown if the register already has a card in it.
     */
    public void assignRandomCard(int register) {
        int i;

        if (this.registers[register] != null) {
            throw new UnsupportedOperationException("Register already filled!");
        }

        if (this.registerLocked[register]) {
            throw new UnsupportedOperationException("Register is locked!");
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
     * @todo verify if this function is really required.
     */
    public void retrieveCards() {
        for (int i = 0; i <
                this.registers.length; i++) {
            if (this.getRegisterLockStatus(i) == false) {
                this.registers[i].setWithPlayer(-1);
                this.registers[i] = null;
            }

        }

        for (int i = 0; i <
                this.hand.length; i++) {
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
        if (board.isMoveValid(this.position, direction) == false) {
            return (false);
        } else {
            /* Is there a robot on that square? */
            playerRef = board.getPositionOccupier(this.position.neighbour(direction));
            /* Move that robot, same direction 
             * Use recursion to allow a whole chain of moves to fail if the
             * 'last' one fails.
             */
            if (playerRef != null) {
                if (playerRef.move(direction) == false) {
                    return (false);
                }

            }
        }
        this.updatePosition(direction);
        return (true);
    }

    private void rotate(Direction turn) {
        /* Rotate */
        switch (turn) {
            case LEFT:
                switch (this.orientation) {
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
                switch (this.orientation) {
                    case UP:
                        this.orientation = Direction.RIGHT;
                        break;

                    case RIGHT:
                        this.orientation = Direction.DOWN;
                        break;

                    case DOWN:
                        this.orientation = Direction.LEFT;
                        break;

                    case LEFT:
                        this.orientation = Direction.UP;
                        break;

                }


                break;
            case UP:
                // Do nothing!
                break;
            case DOWN:
                switch (this.orientation) {
                    case UP:
                        this.orientation = Direction.DOWN;
                        break;

                    case DOWN:
                        this.orientation = Direction.UP;
                        break;

                    case LEFT:
                        this.orientation = Direction.RIGHT;
                        break;

                    case RIGHT:
                        this.orientation = Direction.LEFT;
                        break;

                }


                break;
        }

    }

    /**
     * Update the Robot's position. This is private as any moves should be done
     * with the move() method, which allows pushing, etc. Use setPosition() to
     * set the position of the Robot, e.g. when respawning.
     * @see Player.move()
     * @see Player.setPosition()
     * @param direction
     */
    private void updatePosition(Direction direction) {
        switch (direction) {
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

    /**
     * Apply a card move to our position. Converts an abstract move, (from a card)
     * into a series of moves in the appropriate direction, depending on our
     * orientation
     * @param cardMove The move to do, usually taken straight from a card. Only
     *                  card-valid moves are permitted (rotate XOR move)
     * @return TRUE or FALSE depending on whether the move was successfful or not.
     *          Rotations always succeed, as they cannot be interfered with.
     *          Forward 2 or 3 moves may fail after the first (or second) part of
     *          the move - the return value only signifies that the move didn't
     *          completely succeed.
     */
    public boolean doCardMove(Move cardMove) throws UnsupportedOperationException {
        /* movement NXOR rotation = error condition */
        if (!((cardMove.forward == 0) ^ (cardMove.turn == null))) {
            throw new UnsupportedOperationException("Invalid Move parameters!");
        }

        if (cardMove.forward == 0) {
            this.rotate(cardMove.turn);
            return (true);
        } else if (cardMove.forward == -1) {
            /* Move backwards */
            switch (this.orientation) {
                case UP:
                    return (this.move(Direction.DOWN));
                case DOWN:
                    return (this.move(Direction.UP));
                case LEFT:
                    return (this.move(Direction.RIGHT));
                case RIGHT:
                    return (this.move(Direction.LEFT));
            }

        } else {
            /* Do N moves */
            for (int i = 0; i <
                    cardMove.forward; i++) {
                if (this.move(this.orientation) == false) {
                    return (false);
                }

            }
        }
        return (true);
    }
}
