package unocardgame;

/**
 *
 * Author: Anup Sheena 
 * Date Created: 2019.04.10 
 * Description:logic for game
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Uno {

    private Card middleCard; // the card in the middle
    private Deck deck; // the decck
    private ArrayList<Card> discardPile; //discard pile
    private ArrayList<Player> playerList; //list of players
    private Scanner choice;
    private int numberPlayers;
    private int pick; // players pick
    boolean skip; //whether or not to skip players turn
    boolean reverse = false; //default value of reverse is false

    public Uno() {
        deck = new Deck();
        deck.shuffle();
        middleCard = getStartingCard();
        discardPile = new ArrayList<Card>();
        discardPile.add(middleCard);
        choice = new Scanner(System.in);
        playerList = new ArrayList<Player>();
    }

    //gets number of players that would like to play from user
    public void getNumberofPlayers() {
        System.out.print("How many players would u like to play with?");
        numberPlayers = choice.nextInt();
        for (int i = 1; i <= numberPlayers; i++) {
            String playerName = "Player " + i;
            playerList.add(new Player(playerName));
        }
    }

    //the actual game
    public void game() {
        int maxTurn = numberPlayers;
        int i = 0;
        //if reverse varaible is false regular loop
        if (!reverse) {
            while (!gameOver(playerList)) {
                for (i = 0; i < maxTurn; i++) {
                    skip = false;
                    reverse = false;
                    playGame(playerList.get(i));
                    if (skip) {
                        i++;
                    }
                    //if reverse variable is changed true during execution of regular loop
                    //reverses through every player starting at current, then exits loop
                    if (reverse) {
                        for (i = i - 1; i >= 0; i--) {
                            if (i == -1) {
                                i = maxTurn - 1;
                            }
                            skip = false;
                            playGame(playerList.get(i));
                        }
                        break;
                    }
                }
            }
        }

        ///if reverse varaible is true, reverse loop
        if (reverse) {
            while (!gameOver(playerList)) {
                for (int j = (maxTurn - 1); j >= +0; j--) {
                    skip = false;
                    reverse = true;
                    playGame(playerList.get(j));
                    if (skip) {
                        j--;
                    }
                    //if reverse variable is changed to false during execution of reverse loop
                    //regular loops through every player starting at current, then exits loop
                    if (!reverse) {
                        for (j = j + 1; j < maxTurn; j++) {
                            if (j == maxTurn) {
                                j = 0;
                            }
                            skip = false;

                            playGame(playerList.get(j));
                        }
                        break;
                    }
                }
            }
        }
    }
    //gives 7 cards to each player

    public void dealCards() {
        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < playerList.size(); j++) {
                playerList.get(j).pickCards(deck.draw());
            }
        }
    }

    //displays current players card
    public void displayCurrent(Player p) {
        seperate();
        showBoard(p);
        seperate();
    }

    //method for penalty(+2, or +4)
    public void penalty(Player p, int penalty) {
        for (int i = 0; i < penalty; i++) {
            if (deck.isEmpty()) {
                deck = new Deck(discardPile);
            }
            Card pick;
            pick = deck.draw();
            p.pickCards(pick);
            System.out.println("You picked: \n" + pick);
        }
        pause();
        displayCurrent(p);
        p.showCards();
    }

    // checks to see if player has valud card in their hand
    public void checkHand(Player p) {
        if (!hasColor(p) && !hasValue(p)) {
            Card pick = null;
            System.out.println("You dont have a valid card to play, so you have to pick a card.");
            while (!hasColor(p) && !hasValue(p)) {

                pause();
                pick = deck.draw();
                p.pickCards(pick);
                System.out.println("You picked:\n" + pick);
            }
            System.out.println("You recieved a valid card!");
            pause();
            System.out.println("You have the following cards: ");
            p.showCards();
        }
    }

    public int getPick(Player p) {
        System.out.println("Please pick a card:");
        pick = choice.nextInt() - 1;
        while (!isValidChoice(p, pick)) {
            System.out.println("Invalid pick. Please pick a valid card.");
            pick = choice.nextInt() - 1;
        }
        if (p.PlayerCards().get(pick).getColor().equalsIgnoreCase("wild")) {
            System.out.print("Enter a color for this wild card press 1 for Red, 2 for Yellow, 3 for Blue, and 4 for Green");
            int color = choice.nextInt();
            if (color == 1) {
                p.PlayerCards().get(pick).setColor("Red");
            } else if (color == 2) {
                p.PlayerCards().get(pick).setColor("Yellow");
            } else if (color == 3) {
                p.PlayerCards().get(pick).setColor("Blue");
            } else if (color == 4) {
                p.PlayerCards().get(pick).setColor("Green");
            }
        }

        if (p.PlayerCards().get(pick).getValue().equalsIgnoreCase("reverse")) {
            reverse = true;
        }

        if (p.PlayerCards().get(pick).getValue().equalsIgnoreCase("skip")) {
            skip = true;
        }
        return pick;
    }

    //game logic
    public void playGame(Player p) {

        seperate();
        System.out.println(p + ", It is your turn\nThe current card on play is:\n" + "|| " + middleCard + " || ");

        //displays card in middle
        displayCurrent(p);

        //if last card played was +2 player draws 2 cards
        if (middleCard.getValue().equalsIgnoreCase("+2")) {
            penalty(p, 2);
        }

        //if last card played was +4 player draws 4 cards
        if (middleCard.getValue().equalsIgnoreCase("+4")) {
            penalty(p, 4);

            //checks for valid card
            checkHand(p);
            //gets pick from player
            pick = getPick(p);
            Card play = p.throwCard(pick);
            middleCard = play;
            discardPile.add(middleCard);
        }
    }

    private boolean isValidChoice(Player p, int choice) { //checks if card selected matches card in middle

        if (choice <= p.PlayerCards().size()) {

            if (p.PlayerCards().get(choice).getColor().equals(middleCard.getColor()) || p.PlayerCards().get(choice).getValue().equalsIgnoreCase(middleCard.getValue()) || p.PlayerCards().get(choice).getColor().equalsIgnoreCase("wild")) {
                return true;

            } else {
                return false;
            }
        }
        return false;
    }

    private void pause() { //make sure user acknowledges action

        System.out.println("Press enter to continue......");
        choice.nextLine();
    }

    private boolean hasColor(Player p) { //check to see if any card in player's hand matches colour in middle

        for (Card c : p.PlayerCards()) {

            if (c.getColor().equals(middleCard.getColor()) || c.getColor().equals("wild")) {
                return true;
            }
        }
        return false;
    }

    private boolean hasValue(Player p) { //check to see if any card in player's hand matches value in middle
        for (Card c : p.PlayerCards()) {
            if (c.getValue().equalsIgnoreCase(middleCard.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void seperate() {

        System.out.println("***********************************************************************************");
    }

    private Card getStartingCard() { //make sure starting card isnt +2, reverse, skip or wild

        Card temp = deck.check();
        while (temp.getValue().equals("+2") || temp.getValue().equalsIgnoreCase("skip") || temp.getValue().equalsIgnoreCase("reverse") || temp.getColor().equalsIgnoreCase("wild")) {
            deck.shuffle();
            temp = deck.check();
        }
        temp = deck.draw();
        return temp;
    }

    //shows players hand
    public void showBoard(Player p) {
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println(playerList.get(i));
            playerList.get(i).hideCards();
        }
        System.out.println("Your hand is:");
        p.showCards();

    }

    //checks to see if game over, if so prints statement
    public boolean gameOver(ArrayList<Player> p) {
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).hasWon()) {
                System.out.println("**************************************************");
                System.out.println("Player " + p.get(i) + " has won");
                System.out.println("**************************************************");
                return true;
            }
        }
        return false;
    }

}
