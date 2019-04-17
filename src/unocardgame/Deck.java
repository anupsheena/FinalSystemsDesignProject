package unocardgame;

/**
 *
 * Author: Anup Sheena 
 * Date Created: 2019.04.10 
 * Description: holds all cards in an uno deck
 *
 */
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {

        deck = new ArrayList<Card>();
        String[] colours = {"Red", "Blue", "Green", "Yellow"};
        String[] values = {"one", "one", "two", "two", "three", "three", "four", "four", "five", "five", "six", "six", "seven", "seven", "eight", "eight", "nine", "nine", "zero", "skip", "reverse", "+2"}; //normal 
        String[] wildValues = {"+4", "wild"};

//loop to add regular cards
        for (String c : colours) {
            for (String v : values) {
                deck.add(new Card(v, c));
            }
        }
//loop to add wild cards
        for (String i : wildValues) {
            deck.add(new Card(i, "wild"));
        }
    }
    
    //deck constructor in case current deck becomes empty
    public Deck(ArrayList<Card> c) {

        deck = c;
    }

    //checks if deck is empty
    public boolean isEmpty() {

        if (deck.size() > 0) {
            return false;
        }
        return true;
    }

    //shuffles deck
    public void shuffle() {

        Collections.shuffle(deck);

    }

    //draws top card
    public Card draw() {

        return deck.remove(deck.size() - 1);
    }

    //previews top card(used in getting starting card method in uno class) 
    public Card check() {

        return deck.get(deck.size() - 1);
    }

}
