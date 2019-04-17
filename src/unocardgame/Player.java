package unocardgame;

/**
 *
 * Author: Anup Sheena 
 * Date Created: 2019.04.10 
 * Description:player object with a name
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private ArrayList<Card> hand; //array of players hand
    private String name; //player name

    public Player(String name) {

        hand = new ArrayList<>();
        this.name = name;

    }
//returns number of cards in a players hand

    public int numberOfCards() {

        return hand.size();
    }
//returns players hand as an arraylist

    public ArrayList<Card> PlayerCards() {

        return hand;
    }

    // adds a card to players hand
    public void pickCards(Card c) {

        hand.add(c);

    }

    //removes specific card from users hand(by index)
    public Card throwCard(int c) {

        return hand.remove(c);
    }

    //shows all the current users cards
    public void showCards() {
        String c = "|| ";
        for (int j = 0; j < hand.size(); j++) {

            c = c + hand.get(j).getColor();

            c = c + " " + hand.get(j).getValue() + " || ";
        }
        c = c + "\n";
        System.out.print(c);
    }

    //shows empty cards so current player can see amount of cards other players have
    public void hideCards() {
        String[] card = {" ----- ", "|     |", "|     |", " ----- "};
        String c = "";
        for (int i = 0; i < card.length; i++) {
            for (int j = 0; j < hand.size(); j++) {
                c = c + card[i] + " ";
            }
            c += "\n";
        }
        System.out.print(c);
    }

    //checks to see if user has won
    public boolean hasWon() {
        if (hand.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {

        return this.name;
    }

}
