package ModelStubs;

import java.io.Serializable;
import java.util.List;

import Models.Player;

public interface Role extends Serializable{
    
    List<State> fetchActions(Player previousPlayer, int bigBlind, Player currentPlayer);
}
