package model;

import java.util.ArrayList;

import model.*;
public class Player {

	
	public ArrayList<Card> playerHand;
	
	public ArrayList<Card> getPlayerHand() {
		return playerHand;
	}
	public void setPlayerHand(ArrayList<Card> playerHand) {
		this.playerHand = playerHand;
	}
	private String name;

	
	public String getName() {
		return name;
	}
	public  void setName(String name) {
		
		this.name = name;

	}
	
	
}
