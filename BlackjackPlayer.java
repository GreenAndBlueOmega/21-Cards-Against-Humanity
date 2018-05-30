package models;

import java.util.ArrayList;

public class BlackjackPlayer extends Player {
	private ArrayList<PlayingCard> hand;
	private boolean isBusted;
	
	public ArrayList<PlayingCard> getHand() {
		return this.hand;
	}
	
	public void setHand(ArrayList<PlayingCard> hand) {
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