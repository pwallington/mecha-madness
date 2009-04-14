/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * @todo More JavaDoc
 * @author pwallington
 */
public class Card {

    private Move move;
    private int index;
    private int priority;
    private int withPlayer;
    private int lockedInRegister;

    /**
     * Create a new Card object WITHOUT a deck position!
     * <p> Should only be used to create 'abstract' cards.
     * @param priority The priority value of the card to be created.
     *              Valid values are multiples of 10, 10 through 840.
     *              Card info is automatically setup based on the priority.
     * @throws java.lang.IllegalArgumentException
     */
    public Card(int priority) {
        this.index = -1;
        this.priority = priority;
        this.withPlayer = -1;
        this.lockedInRegister = -1;
        this.setMove(priority);
    }

    /**
     * Create a new Card object WITH a deck position
     * <p> Should only be used by the Deck during initialisation.
     * @param priority The priority value of the card to be created.
     *              Valid values are multiples of 10, 10 through 840.
     *              Card info is automatically setup based on the priority.
     * @param index The index of the card in the 'ordered' stack.
     * @throws java.lang.IllegalArgumentException
     */
    public Card(int priority, int index) {
        this.index = index;
        this.priority = priority;
        this.withPlayer = -1;
        this.lockedInRegister = -1;
        this.setMove(priority);
    }

    public int getWithPlayer() {
        return this.withPlayer;
    }

    public void setWithPlayer(int withPlayer) {
        if (this.lockedInRegister != -1) {
            throw new java.lang.UnsupportedOperationException("Cannot change assignment of a locked card!");
        }
        this.withPlayer = withPlayer;
    }

    public int getPriority() {
        return this.priority;
    }

    public Move getMove() {
        return this.move;
    }

    public int getLockedInRegister() {
        return lockedInRegister;
    }

    public void setLockedInRegister(int lockedInRegister) {
        if ((this.lockedInRegister != -1) && (lockedInRegister != -1)) {
            throw new java.lang.UnsupportedOperationException("Card already locked in register!");
        }
        if ((this.lockedInRegister == -1) && (lockedInRegister == -1)) {
            throw new java.lang.UnsupportedOperationException("Card already unlocked!");
        }
        this.lockedInRegister = lockedInRegister;
    }

    /**
     * Tell the player holding this card that they no longer have it.
     * @todo Finalise where this processing should really happen!
     */
    public void retrieveFromPlayer() {
        if (this.withPlayer == -1) {
            throw new java.lang.UnsupportedOperationException("Card not with a player");
        }
        throw new java.lang.UnsupportedOperationException("Not Yet Implemented");
    }

    /**
     * Get the deck index of the card. <p>
     * NOTE: if the index is -1 then the card IS NOT A CARD IN THE DECK
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set up the Move member of the class
     * <p> Should only be used by the Card() constructors!
     * @param priority The priority value of the card being created.
     *              Valid values are multiples of 10, 10 through 840.
     *              Card info is automatically setup based on the priority.
     * @throws java.lang.IllegalArgumentException
     */
    private void setMove(int priority) throws java.lang.IllegalArgumentException {
        /* Throw an exception if it's not a valid priority */
        if (priority < 10 || priority > 840 || (priority % 10) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid Card Priority");
        }

        if (priority <= 60) {
            /* U-Turn Cards: 10 - 60 */
            this.move.turn = Direction.DOWN;
            this.move.forward = 0;
        } else if (priority <= 420) {
            /* Turn cards: 70-420, alternate Left/Right */
            this.move.forward = 0;
            switch (priority % 20) {
                /* Odd cards are left, evens are right */
                case 0:
                    this.move.turn = Direction.RIGHT;
                    break;
                case 10:
                    this.move.turn = Direction.LEFT;
                    break;
            }
        } else {
            this.move.turn = Direction.UP;
            /* Forward Movement Cards: 430 - 840 */
            if (priority <= 480) {
                this.move.forward = -1;
            } else if (priority <= 660) {
                this.move.forward = 1;
            } else if (priority <= 780) {
                this.move.forward = 2;
            } else if (priority <= 840) {
                this.move.forward = 3;
            }
        }
    }
}

