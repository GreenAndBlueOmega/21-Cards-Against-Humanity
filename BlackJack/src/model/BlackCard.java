package model;

import enums.Suits;
import enums.Value;

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
	
	public BlackCard(Suits cardSuit, Value cardValue) {
	super(cardSuit,cardValue);	
	}
//	public BlackCard(int draw, int play) {
//		setDraw(draw);
//		setPlay(play);
//	}
	
	@Override
	public String toString() {
		String info = 
//				getText() 
				 "\nDraw: " + getDraw() + "\tPlay: " + getPlay();
		return info;
	}
}