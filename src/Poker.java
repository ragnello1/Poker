import java.util.*;

public class Poker {
    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private final Player player;
    private List<Card> deck;
    private final Scanner in;
    private int chips = 25;
    private int bet;


    public Poker() {
        this.player = new Player();
        this.in = new Scanner(System.in);
    }

    private void bet() {
        do {
            System.out.println("Please enter an amount of chips to wager. You can bet between 1 and 25 chips");
            System.out.println("You currently have " + chips + " chips.");
            bet = in.nextInt();
            if (chips < bet) {
                System.out.println("You do not have " + bet + " chips. Please wager " + chips + " or fewer chips");
            }
            if (bet < 1) {
                System.out.println("You must bet at least 1 chip");
            }
            if (bet > 25) {
                System.out.println("You can only bet up to 25 chips");
            }
        } while (bet > chips || bet < 1 || bet > 25);
        String confirm = "";
        System.out.println("You are currently betting " + bet + " chips. Do you want to continue? Y/N");
        boolean check = true;
        while(check) {
            confirm = in.nextLine();
            confirm = confirm.toUpperCase();
            if(confirm.equals("N")){
                play();
            }
            else if(confirm.equals("Y")) {
                check = false;
            }
        }
    }

    public void play() {
        bet();
        newRound();
        shuffleAndDeal();
        takeTurn();
        roundEnd();
    }

    public void shuffleAndDeal() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);  // shuffles the deck

        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deal 5 cards to the
        }
    }

    ////////// PRIVATE METHODS /////////////////////////////////////////////////////

    private void initializeDeck() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));     // adds 52 cards to the deck (13 ranks, 4 suits)
            }
        }
    }

    private void newRound(){
        player.reset();
    }

    private void takeTurn() {
        showHand();
        int numTrade = -1;
        System.out.println("How many cards will you trade? (Up to 3 cards.)");
        while(numTrade < 0 || numTrade > 3){
            numTrade = in.nextInt();
            if (numTrade < 0) {
                System.out.println("You must trade at least 0 cards");
            }
            if (numTrade > 3) {
                System.out.println("You must trade 3 or fewer cards");
            }
            if (numTrade > -1 && numTrade < 4) {
                break;
            }
        }

        int cardInput;
        if(numTrade != 0) {
            int cardNum = 1;
            boolean count = false;
            showHand();
            String suit = "";
            for (int i = 0; i < numTrade; i++) {
                System.out.print("\nWhich card (" + cardNum + "/" + numTrade + ") would you like to trade?\n");

                if(!count) {
                    System.out.print("(Please enter the position of the card rather than the name: Left-most being 1, Right-most being 5.)\n");
                    count = true;
                }
                cardNum++;
                cardInput = (in.nextInt() - 1);
                suit = String.valueOf(player.hand.get(cardInput)).substring(1);
                int temp = Card.getOrderedSuit(suit);
                player.hand.remove(cardInput);
                player.rank.remove(cardInput);
                int place = player.suit.indexOf(temp);
                player.suit.remove(place);
                if(i + 1 != numTrade) {
                    showHand();
                }
            }
            for (int j = 0; j < numTrade; j++) {
                player.takeCard(deck.remove(0));
            }
        }
        else {
            roundEnd();
        }
    }

    private void roundEnd() {
        showHand();
        player.handType();
        switch (player.type) {
            case 1 -> {
                chips += bet * 100;
                System.out.println("You won with a Royal Flush!");
            }
            case 2 -> {
                chips += bet * 50;
                System.out.println("You won with a Straight Flush!");
            }
            case 3 -> {
                chips += bet * 25;
                System.out.println("You won with a Four of a Kind!");
            }
            case 4 -> {
                chips += bet * 15;
                System.out.println("You just won with a Full House!");
            }
            case 5 -> {
                chips += bet * 10;
                System.out.println("You won with a Flush!");
            }
            case 6 -> {
                chips += bet * 5;
                System.out.println("You won with a Straight!");
            }
            case 7 -> {
                chips += bet * 3;
                System.out.println("You won with a Three of a Kind!");
            }
            case 8 -> {
                chips += bet * 2;
                System.out.println("You won with a Two Pair!");
            }
            case 9 -> {
                chips += bet;
                System.out.println("You won with a Pair Above Jack!");
            }
            default -> {
                chips -= bet;
                System.out.println("YOU LOSE! You have lost " + bet + "chips!");
            }
        }
        System.out.println("This round is now over, but you can place a new bet and continue playing." + "\nYou now have " + chips + " chips." + "\nWould you like to continue? (Y/N)");
        String input;
        while(true){
            input = in.nextLine();
            input = input.toUpperCase();
            if(input.equals("Y")) {
                play();
            }
            if(input.equals("N")) {
                gameEnd();
            }
        }
    }

    private void showHand() {
        System.out.println("\nPLAYER hand: " + player.getHand());
    }

    private void gameEnd() {
        if (chips <= 0) {
            System.out.println("Out of chips! Thanks for playing!");
        } else {
            System.out.println("Your final chip count was " + chips + " chips.");
        }
        System.exit(0);
    }

    ////////// MAIN METHOD /////////////////////////////////////////////////////////

    public static void main(String[] args) {
        System.out.println(
                "                                                                                                                    \n" +
                "PPPPPPPPPPPPPPPPP                              kkkkkkkk                                                             \n" +
                "P::::::::::::::::P                             k::::::k                                                             \n" +
                "P::::::PPPPPP:::::P                            k::::::k                                                             \n" +
                "PP:::::P     P:::::P                           k::::::k                                                             \n" +
                "  P::::P     P:::::P        ooooooooooo         k:::::k    kkkkkkk         eeeeeeeeeeee         rrrrr   rrrrrrrrr   \n" +
                "  P::::P     P:::::P      oo:::::::::::oo       k:::::k   k:::::k        ee::::::::::::ee       r::::rrr:::::::::r  \n" +
                "  P::::PPPPPP:::::P      o:::::::::::::::o      k:::::k  k:::::k        e::::::eeeee:::::ee     r:::::::::::::::::r \n" +
                "  P:::::::::::::PP       o:::::ooooo:::::o      k:::::k k:::::k        e::::::e     e:::::e     rr::::::rrrrr::::::r\n" +
                "  P::::PPPPPPPPP         o::::o     o::::o      k::::::k:::::k         e:::::::eeeee::::::e      r:::::r     r:::::r\n" +
                "  P::::P                 o::::o     o::::o      k:::::::::::k          e:::::::::::::::::e       r:::::r     rrrrrrr\n" +
                "  P::::P                 o::::o     o::::o      k:::::::::::k          e::::::eeeeeeeeeee        r:::::r            \n" +
                "  P::::P                 o::::o     o::::o      k::::::k:::::k         e:::::::e                 r:::::r            \n" +
                "PP::::::PP               o:::::ooooo:::::o     k::::::k k:::::k        e::::::::e                r:::::r            \n" +
                "P::::::::P               o:::::::::::::::o     k::::::k  k:::::k        e::::::::eeeeeeee        r:::::r            \n" +
                "P::::::::P                oo:::::::::::oo      k::::::k   k:::::k        ee:::::::::::::e        r:::::r            \n" +
                "PPPPPPPPPP                  ooooooooooo        kkkkkkkk    kkkkkkk         eeeeeeeeeeeeee        rrrrrrr            \n" +
                "                                                                                                                    ");
        new Poker().play();
    }
}

