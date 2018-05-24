package model;

import java.lang.reflect.Array;
import java.util.Random;

import enums.Suits;
import enums.Value;



public class CardSet {

	private Value value;
	private Suits suit;
	private int assignedCardValue;
	
	private Value getValue() {
	
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
		
	}
	public Suits getSuit() {
		return suit;
	}
	public void setSuit(Suits suit) {
		this.suit = suit;
		Random selectSuit = new Random();
//		Suits.values().
		Suits[] cardSuits = Suits.values();
		suit = cardSuits[selectSuit.nextInt(cardSuits.length)];
	}
	public int getAssignedCardValue() {
		return assignedCardValue;
	}
	public void setAssignedCardValue(Value value) {
		
		switch (value) {
		
		
		case ACE:
			this.assignedCardValue = 1;
		case TWO: 
			this.assignedCardValue = 2;
		case THREE: 
			this.assignedCardValue = 3;
		case FOUR: 
			this.assignedCardValue = 4;
		case FIVE: 
			this.assignedCardValue = 5;
		case SIX: 
			this.assignedCardValue = 6;
	case SEVEN: 
		this.assignedCardValue = 7;
	case EIGHT: 
		this.assignedCardValue = 8;
	case NINE: 
		this.assignedCardValue = 9;
	case TEN: 
		this.assignedCardValue = 10;
	case JACK: 
		this.assignedCardValue = 10;
	case QUEEN: 
		this.assignedCardValue = 10;
	case KING: 
		this.assignedCardValue = 10;
		}
	}
	
	
	
	
}
