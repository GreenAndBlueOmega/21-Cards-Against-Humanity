package controllers;

import java.util.ArrayList;
import java.util.Collections;

import enums.Suit;
import enums.Value;
import models.Card;
import models.PlayingCard;
import models.BlackjackPlayer;

public class Blackjack {
	
	private ArrayList<BlackjackPlayer> players = new ArrayList<>();
	private static ArrayList<PlayingCard> deck;
	
	public static void playBlackjack() {
		deck = createDeck();
		for (PlayingCard c : deck) {
			System.out.println(c);
		}
	}
	
	private static ArrayList<PlayingCard> createDeck() {
		ArrayList<PlayingCard> cleanDeck = new ArrayList<>();
		Suit[] suits = Suit.values();
		Value[] values = Value.values();
		
		for (int v = 0; v < values.length; v++) {
			for (int s = 0; s < suits.length; s++) {
				PlayingCard c = new PlayingCard(values[v], suits[s]);
				cleanDeck.add(c);
			}
		}
		shuffleCards(cleanDeck);
		return cleanDeck;
	}
	
	private static void dealCards(ArrayList<BlackjackPlayer> players) {
		
	}
	
	private static void shuffleCards(ArrayList<PlayingCard> deck) {
		for (int c = 0; c < 7; c++) {
			Collections.shuffle(deck);
		}
	}
	
	private void takeTurn(BlackjackPlayer p) {
		
	}
	
	private static void declareWinner(BlackjackPlayer p) {
		
	}
}