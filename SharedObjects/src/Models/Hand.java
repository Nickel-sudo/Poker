package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Card card1;
    Card card2;

    public Hand(Card card1, Card card2){
        this.card1 = card1;
        this.card2 = card2;
    }

    public List<Card> getHand(){
        return new ArrayList<>(Arrays.asList(this.card1, this.card2));
    }

    @Override
    public String toString(){

        return this.card1.toString() + " " + this.card2.toString();
    }
}
