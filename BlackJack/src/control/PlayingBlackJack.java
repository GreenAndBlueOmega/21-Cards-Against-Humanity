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
	static ArrayList<BlackjackPlayer> currentPlayers = new ArrayList<>();
	private static int assignedCardValue;
	static ArrayList<Card> hand = new ArrayList<Card>();
	static int cardValue;
	static boolean loop = true;
	static boolean loop2 = true;
	static boolean youWin = true;
	static int handsValue = 0;
	public static void run() {
		menu();
	}

	public static void menu() {

		do {
			String[] options = { "Play Game", "quit" };
			int option = ConsoleIO.promptForMenuSelection(options, false);
			if (option == 1) {
				hand.clear();
				makePlayers();
				subMenu();
			}
			if (option == 2) {
			
				loop = false;
			}

		} while (loop);

	}
	
	public static void quit() {
		loop2 = false;
	}

	public static void stand() {
		
	}
	
	public static void makePlayers() {

		String name = ConsoleIO.promptForInput("What is this players name", true);
		BlackjackPlayer neuChicken = new BlackjackPlayer(name);
		if (name.isEmpty()) {

			for (int i = 0; i < 1; i++) {

				name = "Player" + (i + 1);
			}
			currentPlayers.add(neuChicken);
		}
		System.out.println(name + " Please Choice an Option: ");
		Deck.newDeck();
		Deck.shuffleTime(deck);
		firstDraw();
		playerCards();
	}

	public static ArrayList<Card> playerCards() {
		Deck.gettingCards();
		ArrayList<Card> hand = null;

		for (BlackjackPlayer cP : currentPlayers) {
			hand = cP.getHand();
			hand.add(Deck.deck.get(0));
			
			hand.add(Deck.deck.get(0));
			cP.setHand(hand);

		}
		return hand;
	}

	public static void subMenu() {
		do {
		String[] options = {
		"Hit", "Stand", "Give Up" 		
		};
	int menuChoice = ConsoleIO.promptForMenuSelection(options, false);
	if(menuChoice == 1) {
		Deck.shuffleTime(deck);
		draw();
		//playerWins();
	}
	if(menuChoice == 2) {
		System.out.println("You Lose");
		stand();
	}
	if(menuChoice == 3) {
		System.out.println("You Lose");
		quit();
	}
		
		}while(loop2);
	}

	
	public static void draw() {
//		Deck.newDeck();
		hand.add(Deck.deck.get(0));
		Deck.deck.remove(0);
		System.out.println(hand);
		//calculateHandValue();
		checkWins();
	}
	public static void firstDraw() {
		for(int i = 0; i < 2; i++) {	
		//Deck.newDeck();
		hand.add(Deck.deck.get(0));
		Deck.deck.remove(0);
		}
		System.out.println(hand);
		calculateHandValue();
		
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
		if (playerCards().size() >= 12) {
			switch (value) {
			case ACE:
				this.assignedCardValue = 1;
			}
		}

	}

	public static void calculateHandValue() {
		 handsValue = 0;
		for (int i = 0; i < hand.size(); i++) {
			cardValue = hand.get(i).getValue().ordinal() + 1;
			if(cardValue > 10) {
			
//			if (Value.JACK.ordinal() > 10 || Value.QUEEN.ordinal() > 10 || Value.KING.ordinal() > 10) {
				cardValue = 10;
				//Value.JACK.ordinal() = 10;
//			}
			}
			if (cardValue ==Value.ACE.ordinal() && handsValue > 11) {
				cardValue -= 10;
			}
			else if(cardValue ==Value.ACE.ordinal() && handsValue < 11) {
				cardValue = 1;
			}
			handsValue += cardValue; 
		}
		System.out.println(handsValue);
	}
	public static void checkWins() {
		calculateHandValue();
		if (handsValue == 21) {
			System.out.println("Congratulations You WON " + "YOU GET TO STAY HERE" );
			EndGame();
		
		}
		else if(handsValue > 21) {
			System.out.println("You Busted " + "Try Again in the Next Life ");
			EndGame();
		}
		
	}
	public static void EndGame() {
		ConsoleIO.promptForInput(" ", true);
		System.out.println("Just Kidding");
		boolean retry = false;
	do {	
			
			String[] choices = {
			"Play Again", "Quit"		
			};
			int choice = ConsoleIO.promptForMenuSelection(choices, false);
			if(choice == 1 ) {
				hand.clear();
				currentPlayers.clear();
				retry = true;
				loop2 = false;
			}
			if(choice == 2) {
				retry = false;
				loop2 = false;
				loop = false;
			}
		
	}while(!retry);
	}

}
