package Enums;

import ModelStubs.State;

public enum PlayerActions {

	BET,
    CALL,
    CHECK,
    FOLD,
    RAISE,
    AllIN;

    public State translate(){

    	//States are not implemented yet
		/* 
		 * return switch(this) { case BET -> new Bet(); case CALL -> new Called(); case
		 * CHECK -> new Checked(); case FOLD -> new Folded(); case RAISE -> new
		 * Raised(); case AllIN -> new AllIn(); };
		 */
    	return null;
    }
}
