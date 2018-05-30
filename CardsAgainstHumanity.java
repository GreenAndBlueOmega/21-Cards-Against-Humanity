package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import lib.ConsoleIO;
import models.BlackCard;
import models.CAHPlayer;
import models.Card;
import models.WhiteCard;

public class CardsAgainstHumanity {

	private static ArrayList<CAHPlayer> players;
	private static ArrayList<WhiteCard> whiteDeck;
	private static ArrayList<BlackCard> blackDeck;
	private final static int win = 7;
	
	public static void cardsMainMenu() {
		boolean keepPlaying = true;
		
		do {
			String[] options = {"Play", "Options"};
			int menuChoice = ConsoleIO.promptForMenuSelection(options, true);
			
			switch(menuChoice) {
				case 1:
					playCardsAgainstHumanity();
					break;
				case 2:
					break;
				case 0:
					keepPlaying = false;
			}
		} while (keepPlaying);
	}
	
	private static void playCardsAgainstHumanity() {
		players = initializePlayers();
		whiteDeck = createWhiteDeck(importDeck("White"));
		blackDeck = createBlackDeck(importDeck("Black"));
		
		System.out.println(blackDeck.get(0));
		
		for (CAHPlayer p : players) {
			demoTurn(p);
			ArrayList<WhiteCard> h = p.getHand();
			int count = 1;
			for (WhiteCard c : h) {
				System.out.print(count + ". " + c);
				count++;
			}
		}
	}	
	
	private static void demoTurn(CAHPlayer player) {
		ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
		
		for (int c = 0; c < win; c++) {
			WhiteCard wc = whiteDeck.get(0);
			hand.add(wc);
			whiteDeck.remove(0);
		}
		
		player.setHand(hand);
	}
	
	private static ArrayList<CAHPlayer> initializePlayers() {
		ArrayList<CAHPlayer> newPlayers = new ArrayList<>();
		int numOfPlayers = ConsoleIO.promptForInt("How many players are there?: ", 0, Integer.MAX_VALUE);
		for (int c = 0; c < numOfPlayers; c++) {
			String name = ConsoleIO.promptForInput("What is the name of Player " + (c + 1) + "? ", false);
			CAHPlayer p = new CAHPlayer(name);
			newPlayers.add(p);
		}
		return newPlayers;
	}
	
	private static ArrayList<WhiteCard> createWhiteDeck(String[] cards) {
		ArrayList<WhiteCard> newWhiteDeck = new ArrayList<>();
		
		for (String s : cards) {
			WhiteCard c = new WhiteCard(s);
			newWhiteDeck.add(c);
		}
		shuffle(newWhiteDeck);
		
		return newWhiteDeck;		
	}
	
	private static ArrayList<BlackCard> createBlackDeck(String[] cards) {
		ArrayList<BlackCard> newBlackDeck = new ArrayList<>();
		
		for (String s : cards) {
			int draw = 0;
			int play = 0;
			if (s.contains("________")) {
				String currentCard = s;
				currentCard.replace("________", "_");
				char[] letters = currentCard.toCharArray();
				for (char a : letters) {
					if (a == '_') {
						play++;
					}
				}
			}
			
			BlackCard b = new BlackCard(draw, play, s);
			newBlackDeck.add(b);
		}
				
		shuffle(newBlackDeck);
		
		return newBlackDeck;
	}
	
	private static void shuffle(ArrayList<? extends Card> deck) {
		//Shuffling 7 times to properly shuffle
		for (int c = 0; c < 7; c++) {
			Collections.shuffle(deck);
		}
	}
	
	private static String[] importDeck(String type) {
		String path = ConsoleIO.promptForInput("Please enter the directory of the " + type + " deck (include the extension): ", false);
//		String path = "decks/default_white.txt";
		String wall = "";
		String[] cards = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			int content;
			while ((content = fis.read()) != -1) {
				wall += (char) content;
			}	
			
			cards = wall.split("\\n");
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return cards;
	}
}