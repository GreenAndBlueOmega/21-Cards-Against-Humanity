package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Suits;
import enums.Value;
import model.*;

public class Deck {

	public static ArrayList<Card> deck;

	public static ArrayList<Card> gettingCards() {
		newDeck();
		shuffleTime(deck);
		return deck;
	}

	public static void newDeck() {
		deck = new ArrayList<Card>();

	Card establishCards = new Card(Value.ACE, Suits.SPADES);
			for(Suits cardSuit:Suits.values())
	{
		for (Value cardValue : Value.values()) {
			deck.add(establishCards = new Card(cardValue, cardSuit));
		} 

	}
}
	public static void shuffleTime(ArrayList<Card> deck) {
			
			 Collections.shuffle(deck);
	
	}
	
}
	
	


