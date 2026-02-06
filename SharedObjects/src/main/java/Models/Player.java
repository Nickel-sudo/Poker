package Models;

import java.io.Serializable;
import java.util.Objects;

import ModelStubs.Role;
import ModelStubs.State;
import Network.GameDiscoveryBroadcaster;
import Network.GameDiscoveryListener;

public class Player implements Serializable{

    String username;
    boolean isHost;
    int jetons;
    GameDiscoveryBroadcaster broadcaster;
    Role role = null;
    Hand hand = null;
    boolean currentPlayer = false;
    boolean startPlayer = false;
    State state;
    boolean isPrevious = false;
    int jetonsInGame = 0;
    Player nextPlayer;
    Player previousPlayer; 
    String id;
    int raisedOrBetOrCalled = 0;
    int score = 0;

    public Player(String username, boolean isHost){
        this.username = username;
        this.isHost = isHost;
    }

    public String getUsername(){
        return this.username;
    }

    public boolean getIsHost(){
        return this.isHost;
    }

    public int getJetons(){
        return this.jetons;
    }

    public void setJetons(int jetons){
        this.jetons = jetons;
    }

    public GameDiscoveryBroadcaster getBroadcast(){
        return this.broadcaster;
    }

    public void setBroadcaster(GameDiscoveryBroadcaster broadcaster){
        this.broadcaster = broadcaster;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public Hand getHand(){
        return this.hand;
    }

    public void setIsCurrentPlayer(boolean currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public boolean isCurrentPlayer(){
        return this.currentPlayer;
    }

    public void setIsStartPlayer(boolean startPlayer){
        this.startPlayer = startPlayer;
    }

    public boolean isStartPlayer(){
        return this.startPlayer;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

    public void setIsPreviousPlayer(boolean isPrevious){
        this.isPrevious = isPrevious;
    }

    public boolean isPreviousPlayer(){
        return this.isPrevious;
    }

    public void setJetonsInGame(int jetonsInGame){
        this.jetonsInGame = jetonsInGame;
    }

    public int getJetonsInGame(){
        return this.jetonsInGame;
    }

    public void setNextPlayer(Player nextPlayer){
        this.nextPlayer = nextPlayer;
    }

    public Player getNextPlayer(){
        return this.nextPlayer;
    }

    public void setPrevPlayer(Player prevPlayer){
        this.previousPlayer = prevPlayer;
    }

    public Player getPrevPlayer(){
        return this.previousPlayer;
    }

    public void setPlayerId(String id){
        this.id = id;
    }

    public String getPlayerId(){
        return this.id;
    }

    public void setRaisedOrBetOrCalled(int raisedOrBetOrCalled){
        this.raisedOrBetOrCalled = raisedOrBetOrCalled;
    }

    public int getRaisedOrBetOrCalled(){
        return this.raisedOrBetOrCalled;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
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
        Player player = (Player) obj;
        return Objects.equals(this.id, player.id);
    }

        @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
