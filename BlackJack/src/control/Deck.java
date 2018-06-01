package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Suits;
import enums.Value;
import model.*;

public class Deck {

	public static ArrayList<Card> deck;

	public ArrayList<Card> gettingCards() {
		shuffleTime(deck);
		return deck;
	}

	private static void newDeck() {
		deck = new ArrayList<Card>();

	Card establishCards = new Card(Suits.SPADES, Value.ACE);
			for(Suits cardSuit:Suits.values())
	{
		for (Value cardValue : Value.values()) {
			deck.add(establishCards = new Card(cardSuit, cardValue));
		}

	}
}
	public static void shuffleTime(ArrayList<Card> deck) {
	
			 Collections.shuffle(deck);
	
	}
	
}
	
	


