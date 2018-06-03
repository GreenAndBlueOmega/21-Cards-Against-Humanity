package models;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public class CAHPlayer extends Player {

	private int points;
	private ArrayList<WhiteCard> hand;
	private boolean isCardCzar;
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public ArrayList<WhiteCard> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<WhiteCard> hand) {
		this.hand = hand;
	}
	
	public boolean isCardCzar() {
		return isCardCzar;
	}
	
	public void setCardCzar(boolean isCardCzar) {
		this.isCardCzar = isCardCzar;
	}
	
	public CAHPlayer(String name) {
		setName(name);
		setPoints(0);
		setHand(null);
		setCardCzar(false);
	}
	
	public CAHPlayer(String name, int points, ArrayList<WhiteCard> hand, boolean isCardCzar) {
		setName(name);
		setPoints(points);
		setHand(hand);
		setCardCzar(isCardCzar);
	}
	
	@Override
	public String toString() {
		String info = "*** " + getName() + " ***\nPoints: " + getPoints();
		return info;
	}
}