package models;

import java.util.ArrayList;

public class BlackjackPlayer extends Player {
	private ArrayList<Card> hand;
	private boolean isBusted;
	
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public boolean isBusted() {
		return this.isBusted;
	}
	
	public void setBusted(boolean isBusted) {
		this.isBusted = isBusted;
	}
	
	@Override
	public String toString() {
		String retVal = getName() + " has " + (isBusted ? "" : "not ") + "busted.";
		return retVal;
	}
}