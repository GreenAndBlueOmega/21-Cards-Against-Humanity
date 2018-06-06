package model;

import java.util.Random;

import enums.Suits;
import enums.Value;



public class Card {

	private Value value;
	private Suits suit;
	
	
	public Card( Value cardValue, Suits cardSuit) {
		setSuit(cardSuit);
		setValue(cardValue);
	}
	public Value getValue() {
	
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
		
	}
	public Suits getSuit() {
		return suit;
	}
	public void setSuit(Suits suit) {
		Random selectSuit = new Random();

		Suits[] cardSuits = Suits.values();
		suit = cardSuits[selectSuit.nextInt(cardSuits.length)];
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return "" + this.getSuit() + " : " + this.getValue();
		
	}
}
