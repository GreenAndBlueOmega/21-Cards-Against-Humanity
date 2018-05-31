package control;

import java.util.ArrayList;

import lib.ConsoleIO;
import model.*;

public class PlayingBlackJack {

	static ArrayList<Player> currentPlayers = new ArrayList<>();
	
	public static void makePlayers() {
		String name = ConsoleIO.promptForInput("What is this players name", true);
		if (name.isEmpty()) {

			for (int i = 0; i < 4; i++) {

				name = "Player" + i + 1;
			}

		}

	}

}
