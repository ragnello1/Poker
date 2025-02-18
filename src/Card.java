public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + suit; // allows for easy printing of a card
    }

    public static int getOrderedRank(String rank) {
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            switch (rank) {
                case "T": return 10;    // for 10s, Jacks, Queens, Kings,
                case "J": return 11;    // and Aces, we need to apply a
                case "Q": return 12;    // numeric value to simplify the
                case "K": return 13;    // sorting of hands and books.
                case "A": return 14;
            }
        }

        return -1;
    }

    public static int getOrderedSuit(String suit) {
        return switch (suit) {
            case "C" -> 1;
            case "D" -> 2;
            case "H" -> 3;
            case "S" -> 4;
            default -> -1;
        };

    }
}

