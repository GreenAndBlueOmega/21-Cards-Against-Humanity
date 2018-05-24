package controllers;

import java.util.ArrayList;

import enums.Suit;
import enums.Value;
import models.Card;
import models.PlayingCard;
import models.BlackjackPlayer;

public class Blackjack {
	
	private ArrayList<BlackjackPlayer> players = new ArrayList<>();
	private static ArrayList<Card> deck;
	
	public static void playBlackjack() {
		deck = createDeck();
	}
	
	private static ArrayList<Card> createDeck() {
		ArrayList<Card> cleanDeck = new ArrayList<>();
		Suit[] suits = Suit.values();
		Value[] values = Value.values();
		
		for (int v = 0; v < values.length; v++) {
			for (int s = 0; s < suits.length; s++) {
				Card c = new PlayingCard(values[v], suits[s]);
				cleanDeck.add(c);
				System.out.println(c);
			}
		}
		
		return cleanDeck;
	}
	
	private static void dealCards(ArrayList<Card> deck, ArrayList<BlackjackPlayer> players) {
		
	}
	
	private static void shuffleCards(ArrayList<Card> deck) {
		
	}
	
	private void takeTurn(BlackjackPlayer p) {
		
	}
	
	private static void declareWinner(BlackjackPlayer p) {
		
	}
}