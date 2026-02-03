package Enums;

public enum CardPatterns {
	SPADES,
    HEARTS,
    DIAMONDS,
    CLUBS;

    public char getSuit(){

        return switch (this) {
            case SPADES -> '♠';
            case HEARTS -> '♥';
            case DIAMONDS -> '♦';
            case CLUBS -> '♣';
        };
    }
}
