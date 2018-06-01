package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Suits;
import enums.Value;
import lib.ConsoleIO;
import model.*;

public class PlayingBlackJack {

	static Random select = new Random();
	static ArrayList<Card> deck;
	static ArrayList<Player> currentPlayers = new ArrayList<>();
	private int assignedCardValue;

	public static void makePlayers() {
		String name = ConsoleIO.promptForInput("What is this players name", true);
		if (name.isEmpty()) {

			for (int i = 0; i < 4; i++) {

				name = "Player" + i + 1;
			}
		}

	}

	public ArrayList<Card> playerCards() {
		ArrayList<Card> hand = null;

		for (Player cP : currentPlayers) {
			hand = cP.getPlayerHand();
			hand.add(deck.get(0));
			deck.remove(0);

		}

		return hand;

	}

	public int getAssignedCardValue() {
		return assignedCardValue;
	}

	public void setAssignedCardValue(Value value) {

		switch (value) {
		case ACE:
			this.assignedCardValue = 11;
		case JACK:
			this.assignedCardValue = 10;
		case QUEEN:
			this.assignedCardValue = 10;
		case KING:
			this.assignedCardValue = 10;
		}

	}

	public ArrayList<Card> gettingCards() {
		shuffleTime(deck);
		return deck;
	}

	private static void newDeck() {
		deck = new ArrayList<Card>();

		Card establishCards = new Card(Suits.SPADES, Value.ACE);
		for (Suits cardSuit : Suits.values()) {
			for (Value cardValue : Value.values()) {
				deck.add(establishCards = new Card(cardSuit, cardValue));
			}

		}
	}

	public static void shuffleTime(ArrayList<Card> deck) {

		Collections.shuffle(deck);

	}
}
