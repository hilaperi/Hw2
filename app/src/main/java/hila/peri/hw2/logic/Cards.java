package hila.peri.hw2.logic;

import java.util.ArrayList;
import java.util.Collections;

public class Cards {
    private String imageName;
    private int strength;


    public Cards(String imageName, int strength) {
        this.imageName = imageName;
        this.strength = strength;
    }

    public String getImageName() {
        return imageName;
    }


    public int getStrength() {
        return strength;
    }


    public boolean isStronger(Cards card) {
        return this.strength > card.getStrength();
    }

    public static class Deck {
       private ArrayList<Cards> cards;

       public Deck() {
           this.cards = new ArrayList<>();
       }

       public ArrayList<Cards> getCards() {
           return cards;
       }


       public void shuffleCards() {
           Collections.shuffle(cards);
       }

       public Cards getCard() {
           if (!isEmpty()) {
               return cards.remove(0);
           }
           return null;
       }

       public boolean isEmpty() {
           return cards.isEmpty();
       }

       public void addCard(String imageName, int strength) {
           cards.add(new Cards(imageName, strength));
       }
    }
}
