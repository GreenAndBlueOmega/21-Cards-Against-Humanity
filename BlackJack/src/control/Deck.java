package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Suits;
import enums.Value;
import model.CardSet;

public class Deck {

	private static ArrayList<CardSet> deck;

	static Random select = new Random();
	public Deck() {

	}

	public ArrayList<CardSet> gettingCards() {
		shuffleTime();
		return deck;
	}

	private static void newDeck() {
		deck = new ArrayList<CardSet>();

	CardSet setCards = new CardSet(Suits.SPADES, Value.ACE);
			for(Suits cardSuit:Suits.values())
	{
		for (Value cardValue : Value.values()) {
			deck.add(setCards = new CardSet(cardSuit, cardValue));
		}

	}
}
	public static void shuffleTime() {
			try {
			 Collections.shuffle(deck);
			} catch (NullPointerException e) {

			}

	}
	
}
	
	


