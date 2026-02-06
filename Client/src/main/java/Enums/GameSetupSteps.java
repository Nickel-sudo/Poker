package Enums;

public enum GameSetupSteps {

	LOBBY_NAME,
	NUM_PLAYERS,
	NUM_JETONS,
	SMALL_BLIND,
	BIG_BLIND;
	
	public void getPrompt() {
		
		switch(this) {
			case LOBBY_NAME -> System.out.println("Lobby Name: ");
			case NUM_PLAYERS -> System.out.println("Number of Players: ");
			case NUM_JETONS -> System.out.println("Jetons: ");
			case SMALL_BLIND -> System.out.println("Small Blind: ");
			case BIG_BLIND -> System.out.println("Big Blind: ");
		}
	}
	
	public boolean expectsNumeric() {
		return this != LOBBY_NAME;
	}
}
