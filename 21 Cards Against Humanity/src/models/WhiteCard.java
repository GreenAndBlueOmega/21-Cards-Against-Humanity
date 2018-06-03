package models;

import enums.Color;

public class WhiteCard extends Card {

	public WhiteCard(String text) {
		super();
		setText(text);
		setColor(Color.WHITE);
	}
	
	@Override
	public String toString() {
		String info = getText();
		return info;
	}
}