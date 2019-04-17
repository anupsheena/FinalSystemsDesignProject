package unocardgame;

/**
 *
 * Author: Anup Sheena Date Created: 2019.04.10 
 * Description: object for a card with a color and a value
 *
 */
public class Card {

    private String color;
    private String value;

    public Card(String value, String color) {

        this.color = color;
        this.value = value;

    }

    public String getColor() {

        return this.color;
    }

    public String getValue() {

        return this.value;

    }
    
    public void setColor(String color) {
        this.color = color;
        
    }

    @Override
    public String toString() {

        String c = this.getColor() + " " + this.getValue();
        
        return c;

    }
}
