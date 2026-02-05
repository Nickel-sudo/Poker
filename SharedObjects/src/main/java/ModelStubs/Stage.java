package ModelStubs;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

import Models.Card;

public interface Stage extends Serializable{
    
    void updateCommonCards(Supplier<List<Card>> cardsInGame, List<Card> currentCommonCards);
}
