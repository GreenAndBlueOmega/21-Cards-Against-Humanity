package models;

import enums.Color;

public class BlackCard extends Card {

	private int draw;
	private int play;
	
	public int getDraw() {
		return draw;
	}
	
	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public int getPlay() {
		return play;
	}
	
	public void setPlay(int play) {
		this.play = play;
	}
	
	public BlackCard(int draw, int play, String text) {
		setDraw(draw);
		setPlay(play);
		setColor(Color.BLACK);
		setText(text);
	}
	
	@Override
	public String toString() {
		String info = getText() + "\nDraw: " + getDraw() + "\tPlay: " + getPlay();
		return info;
	}
}