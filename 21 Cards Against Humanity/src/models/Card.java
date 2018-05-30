package models;

import enums.Color;

public abstract class Card {
	protected String text;
	protected Color color;
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		String info = getText();
		return info;
	}
}