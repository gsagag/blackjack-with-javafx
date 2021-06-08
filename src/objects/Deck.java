package objects;
public class Deck {

    private Card[] cards = new Card[52];

    public Deck() {
        create();
    }

    public final void create() {
        int i = 0;
        for (Suit suit : Suit.values()) {
            for (Number rank : Number.values()) {
                cards[i] = new Card(suit, rank);
                i++;
            }
        }
    }

    public Card drawCard() {
        Card card = null;
        while (card == null) {
            int index = (int)(Math.random()*cards.length);
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}
