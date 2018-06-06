package model;

import enums.Suits;
import enums.Value;

public class PlayingCard extends Card {
	private Value value;
	private Suits suit;
	
	public Value getValue() {
		return this.value;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public Suits getSuit() {
		return this.suit;
	}
	
	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	
	public PlayingCard(Value value, Suits suit) {
	super(value, suit);
		setValue(value);
		setSuit(suit);
	}
		
//		String v = value.toString();
//			v = v.substring(0, 1).toUpperCase() + v.substring(1).toLowerCase();
//		String s = suit.toString();
//			s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//		String info = v + " of " + s;
	}
