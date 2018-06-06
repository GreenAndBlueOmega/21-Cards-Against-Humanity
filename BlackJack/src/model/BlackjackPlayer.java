package model;

import java.util.ArrayList;

import lib.ConsoleIO;

public class BlackjackPlayer extends Player {
	public static ArrayList<Card> hand;
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
	
	public BlackjackPlayer(String name) {
		setName(name);
	}


	
	
	}
	
//	@Override
//	public String toString() {
//		String retVal = getName() + " has " + (isBusted ? "" : "not ") + "busted.";
//		return retVal;
//	}
