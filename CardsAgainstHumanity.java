package controllers;

import java.util.ArrayList;

import models.BlackCard;
import models.CAHPlayer;
import models.WhiteCard;

public class CardsAgainstHumanity {

	private static ArrayList<CAHPlayer> players;
	private static ArrayList<WhiteCard> whiteDeck;
	private static ArrayList<BlackCard> blackDeck;
	
	public static void playCardsAgainstHumanity() {
		whiteDeck = createWhiteDeck();
		blackDeck = createBlackDeck();
	}
	
	private static ArrayList<WhiteCard> createWhiteDeck() {
		ArrayList<WhiteCard> newWhiteDeck = new ArrayList<>();
		WhiteCard demo = new WhiteCard("Some inappropriate thing.");
		newWhiteDeck.add(demo);
		
		System.out.println(demo);
		
		return newWhiteDeck;
	}
	
	private static ArrayList<BlackCard> createBlackDeck() {
		ArrayList<BlackCard> newBlackDeck = new ArrayList<>();
			BlackCard demo = new BlackCard(0, 0, "Some raunchy prompt");
		newBlackDeck.add(demo);
		
		System.out.println(demo);
		
		return newBlackDeck;
	}
}