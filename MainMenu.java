package controllers;

import lib.ConsoleIO;

public class MainMenu {
	
	public static void run() {
		menu();
	}

	private static void menu() {
		boolean isInvalid = true;
		do {
			String[] options = {"Play Blackjack", "Play Cards Against Humanity"};
			int menuChoice = ConsoleIO.promptForMenuSelection(options, true);
			
			if (menuChoice == 1) {
				Blackjack.playBlackjack();
			} else if (menuChoice == 2) {
				CardsAgainstHumanity.cardsMainMenu();
			} else {
				isInvalid = false;
			}
		} while (isInvalid);
	}
}