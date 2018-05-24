package models;

import enums.Color;
import enums.Suit;
import enums.Value;

public class PlayingCard extends Card {
	private Value value;
	private Suit suit;
	
	public Value getValue() {
		return this.value;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public PlayingCard(Value value, Suit suit) {
		setValue(value);
		setSuit(suit);
		
		if (suit.equals(Suit.CLUBS) || suit.equals(Suit.SPADES)) {
			setColor(Color.BLACK);
		} else {
			setColor(Color.RED);
		}
		
		String v = value.toString();
			v = v.substring(0, 1).toUpperCase() + v.substring(1).toLowerCase();
		String s = suit.toString();
			s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		String info = v + " of " + s;
		setText(info);
	}
}
