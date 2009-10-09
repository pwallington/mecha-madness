/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechamadness;

/**
 * Deck class to keep track of all the cards. The deck looks after instantiating,
 * shuffling and dealing cards.
 * @todo More JavaDoc
 * @author pwallington
 */
public class Deck {

    public static final int deckLength = 84;
    private int currentStackPos;
    /**
     * Raw list of cards, ordered by index.
     * TODO Not sure if this list should maybe be public for ease-of-use
     */
    private Card[] cards;
    /**
     * Ordered indexes of cards in a shuffled deck.
     * Cards that have already been dealt are removed from this list and their
     * indicies are replaced with -1.
     */
    private int[] stack;

    public Deck() {
        this.cards = new Card[Deck.deckLength];
        this.stack = new int[Deck.deckLength];

        for (int i = 0; i < Deck.deckLength; i++) {
            this.cards[i] = new Card((i + 1) * 10, i);
        }

        this.shuffle();
    }

    /**
     * Shuffle the deck.
     * <p> Retrieves all non-locked cards from the players that have them.
     * @calls Card.retrieveFromPlayer() - expects this to do all the work of retreiving a card
     * @throws UnsupportedOperationException
     */
    public void shuffle() {
        // We will use the stack position to help us keep track of the number
        // of unlocked cards we have retrieved so far.
        int i, j;
        for (i = 0, this.currentStackPos = 0; i < Deck.deckLength; i++) {
            // Make sure none of the cards are 'out'
            if (this.cards[i].getLockedInRegister() == -1) {
                this.cards[i].retrieveFromPlayer();

                this.stack[this.currentStackPos] = i;
                this.currentStackPos++;
            }
        }

        /* Only loop through as far as the number of unlocked cards we found
         * This also gives us a way of knowing that we won't shuffle empty
         * entries into the middle of the stack.
         * ONLY USE this.currentStackPos in this loop - not Deck.deckLength */
        for (i = 0; i < this.currentStackPos; i++) {
            // Swap this card with a random one elsewhere in the stack
            j = (int) Math.floor(Math.random() * this.currentStackPos);

            /* Because the stack is a list of indicies we don't need to
             * actually move any card objects around. Makes life simpler
             * and hides the random ordering of the cards.
             */
            if ((this.stack[i] != -1) && (this.stack[j] != -1)) {
                int tmp = this.stack[j];
                this.stack[j] = this.stack[i];
                this.stack[i] = tmp;
            } else {
                throw new UnsupportedOperationException("Found uninitialised Card index in 'unlocked' stack - suspect register locking has gone wrong!");
            }
        }

        // Reset the current position to the start of the card stack.
        this.currentStackPos = 0;
    }

    /**
     * Deals a card out, removes it from the stack.
     * @return The card that is taken off the top of the stack. Caller responsible for
     *         Correctly assigning it to a player and setting the withPlayer field.
     *         If there are no cards left in the stack then <code>null</code>
     *         is returned
     */
    public Card dealCard() {
        // Hooray for indirection...
        int i = this.stack[currentStackPos];
        this.stack[currentStackPos] = -1;

        /* loop through the stack */
        currentStackPos++;
        currentStackPos %= Deck.deckLength;

        /*
         * Don't try and return a card if there isn't one in that slot in the
         * stack or if the card is locked (locked cards shouldn't be in the stack!)
         * So long as no-one else uses currentStackPos this should be fine.
         */
        if ((i == -1) || (this.getCard(i).getLockedInRegister() != -1)) {
            return null;
        } else {
            return (this.cards[i]);
        }
    }

    /** 
     * Method to retrieve a specific card for displaying details etc.
     * The intention is that it be used like this:
     * <p><code>deck.getCard(index).getMove()</code>
     * @param index Index of the requested card.
     * @return The Card with the index specified.
     */
    public Card getCard(int index) {
        return (this.cards[index]);
    }
}
