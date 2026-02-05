package ModelStubs;

import java.io.Serializable;
import java.util.List;

import Models.Card;

public interface Combination extends Serializable {
    
    int SIZE = 5;

    public int getScore(List<Card> combinationHand);
    
}
