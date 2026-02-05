package Models;

import java.io.Serializable;
import java.util.Objects;

import Enums.CardPatterns;
import Enums.CardValues;

public class Card implements Serializable{
    
    private static final long serialVersionUID = 1L;

    CardPatterns cardPattern;
    CardValues cardValue;
    int rank;

    public Card(CardPatterns pattern, CardValues value){
        this.cardValue = value;
        this.cardPattern = pattern;
        this.rank = this.cardValue.getRank();
    }

    public int getRank(){
        return this.rank;
    }

    public String generateASCIIString(){
        
        String rank = "";

        if (this.rank > 10 || this.rank == 14){
            switch (this.rank) {
                case 11:
                    rank = "J";
                    break;
                case 12:
                    rank = "Q";
                    break;
                case 13:
                    rank = "K";
                    break;
                case 14:
                    rank = "A";
                    break;
                case 1:
                    rank = "A";
            }
        } else
            rank = String.valueOf(this.rank);

        char suit = this.cardPattern.getSuit();

        String asciiCard =
                ".-------.\n" +
                "|" + rank + "     |\n" +
                "|   " + suit + "   |\n" +
                "|     " + rank + "|\n" +
                "`-------'";

        return asciiCard;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return Objects.equals(this.rank, card.rank);
    }

    @Override
    public String toString() {
        return generateASCIIString();
    }

    public void setRank(CardValues value) {
        this.cardValue = value;
    }

    public CardPatterns getCardPattern(){
        return this.cardPattern;
    }
}
