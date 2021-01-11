import java.util.*;

public class Player {

    public final List<Card> hand;
    public final List<Integer> rank;
    public final List<Integer> suit;
    public int type = 0;

    public Player() {
        this.hand = new ArrayList<>();
        this.rank = new ArrayList<>();
        this.suit = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
        rank.add(Card.getOrderedRank(card.getRank()));
        suit.add(Card.getOrderedSuit(card.getSuit()));
        sortHand();
    }

    public void reset() {
        hand.clear();
        suit.clear();
        rank.clear();
    }

    private int findCard(Card card) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(card.getRank())) {     // find card by rank
                return i;
            }
        }

        return -1;
    }

    private void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());     // order by suit if
            }                                                                                   // ranks are the same
            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());         // otherwise, by rank
        });
        Collections.sort(rank);
        Collections.sort(suit);
    }

    public void handType() {
        if(royalFlush()){
            type = 1;
        }
        else if(straightFlush()) {
            type = 2;
        }
        else if(fourKind()) {
            type = 3;
        }
        else if(fullHouse()) {
            type = 4;
        }
        else if(flush()) {
            type = 5;
        }
        else if(straight()) {
            type = 6;
        }
        else if(threeKind()) {
            type = 7;
        }
        else if(twoPair()) {
            type = 8;
        }
        else if(jackAbove()) {
            type = 9;
        }
    }

    private boolean royalFlush() {
        return straightFlush() && Card.getOrderedRank(hand.get(4).getRank()) == 14;
    }

    private boolean straightFlush() {
        return straight() && flush();
    }

    private boolean fourKind() {
        return (rank.get(0).equals(rank.get(1)) && rank.get(0).equals(rank.get(2)) && rank.get(0).equals(rank.get(3))) ||
                (rank.get(1).equals(rank.get(2)) && rank.get(1).equals(rank.get(3)) && rank.get(1).equals(rank.get(4)));
    }

    private boolean fullHouse() {
        return twoPair() && threeKind();
    }

    private boolean flush() {
        return (suit.get(0).equals(suit.get(1)) && suit.get(0).equals(suit.get(2)) && suit.get(0).equals(suit.get(3)) && suit.get(0).equals(suit.get(4)));
    }

    private boolean straight() {
        boolean temp = true;
        for(int i = 0; i < hand.size() - 1; i++) {
            if (rank.get(i) != rank.get(i + 1) - 1) {
                temp = false;
            }
        }
        return temp;
    }

    private boolean threeKind() {
        return (rank.get(0).equals(rank.get(1)) && rank.get(0).equals(rank.get(2))) ||
               (rank.get(1).equals(rank.get(2)) && rank.get(1).equals(rank.get(3))) ||
               (rank.get(2).equals(rank.get(3)) && rank.get(2).equals(rank.get(4)));
    }

    private boolean twoPair() {
        return rank.get(0).equals(rank.get(1)) && rank.get(2).equals(rank.get(3)) ||
               rank.get(0).equals(rank.get(1)) && rank.get(3).equals(rank.get(4)) ||
               rank.get(1).equals(rank.get(2)) && rank.get(3).equals(rank.get(4));
    }

    private boolean jackAbove() {
        boolean temp = false;
        for(int i = 0; i < hand.size() - 1; i++) {
            if (rank.get(i) >= 11 && rank.get(i).equals(rank.get(i + 1))) {
                temp = true;
            }
        }
        return temp;
    }
}
