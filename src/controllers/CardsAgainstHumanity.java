package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.ConsoleIO;
import models.BlackCard;
import models.CAHPlayer;
import models.Card;
import models.WhiteCard;

public class CardsAgainstHumanity {

	private static ArrayList<CAHPlayer> players;
	private static ArrayList<WhiteCard> whiteDeck;
	private static ArrayList<BlackCard> blackDeck;
	private static int win;
	private static Stage stage;
	
	public static void cardsMainMenu(Stage primaryStage) {
		stage = primaryStage;
		Scene scene = cardsGUI();
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	
	private static Scene cardsGUI() {		
		Button playGame = new Button("Start Game");
			playGame.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					//cards main menu
					
					//create white deck
						//if players * (number of cards per hand) > number of cards in white deck
							//inform
					
					//create black deck
					
					//choose player to be judge
				}
			}
		);
			
		Button setupPlayers = new Button("Players");
			setupPlayers.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					stage.setScene(playersMenu());
					stage.setFullScreen(true);
				}
			}
		);
			
		Button options = new Button("Options");
			options.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					//import white deck
						//create white cards
							//export as white deck
					//import black deck
						//create black cards
							//export as black deck
					
					//manually choose card czar or choose randomly
					
					//choose win condition
						//default to 7
					
					//choose number of cards in hand
						//default to 7
				}
			}
		);
			
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(playGame, setupPlayers, options);
		
		BorderPane root = new BorderPane();
			root.setTop(menu());
			root.setCenter(vb);
		
		return new Scene(root);
	}
	
	private static MenuBar menu() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
			MenuItem fileNew = new MenuItem("New Game");
			MenuItem fileLoad = new MenuItem("Load Game");
			MenuItem fileExit = new MenuItem("Exit");
				fileExit.setOnAction(e -> Platform.exit());
		
			fileMenu.getItems().addAll(fileNew, fileLoad, fileExit);
		
		menuBar.getMenus().add(fileMenu);
		
		return menuBar;
	}
	
	private static Scene mainScene() {
		VBox playerList = new VBox();
			for (CAHPlayer c : players) {
				Label name = new Label(c.getName());
				playerList.getChildren().add(name);
			}
			
		BorderPane view = new BorderPane();
			view.setTop(menu());
			view.setLeft(playerList);
			
		return new Scene(view);
	}
	
	private static Scene playersMenu() {
		players = new ArrayList<CAHPlayer>();
		
		VBox vb = new VBox();
		
		Button addPlayers = new Button("Add Players");
			addPlayers.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					addPlayer();
				}
			}
		);
		
		Button removePlayers = new Button("Remove Players");
		
		Button viewPlayers = new Button("View Players");
//			viewPlayers.setOnAction(e -> viewPlayers());
		
		Button exit = new Button("Return to Main Menu");
			exit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					stage.setScene(cardsGUI());
					stage.setFullScreen(true);
				}
			}
		);
		
		vb.setPadding(new Insets(20, 20, 20, 20));
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(addPlayers, removePlayers, viewPlayers, exit);
		
		BorderPane bp = new BorderPane();
			bp.setTop(menu());
			bp.setCenter(vb);
		
		return new Scene(bp);
	}
	
	private static void addPlayer() {
		Stage popUpWindow = new Stage();
			popUpWindow.initModality(Modality.APPLICATION_MODAL);
			popUpWindow.setTitle("Add a Player");			
			
		VBox root = new VBox();
		Label userLabel = new Label("Insert the name of the player:");
		final TextField name = new TextField();
			name.setMaxWidth(400);
		Button createAccountButton = new Button("Create Player");
			createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					CAHPlayer p = new CAHPlayer(name.getText());
					players.add(p);
					Alert alert = new Alert(AlertType.CONFIRMATION, name.getText() + " has been created.", ButtonType.OK);
					alert.showAndWait();
					name.clear();
					name.requestFocus();
				}
			}
		);
			
		Button close = new Button("Return to Player Menu");
			close.setOnAction(e -> popUpWindow.close());
		
		root.getChildren().addAll(userLabel, name, createAccountButton, close);
		
		BorderPane bp = new BorderPane();
			bp.setCenter(root);
		
		Scene scene = new Scene(bp);
		
		popUpWindow.setScene(scene);
		popUpWindow.showAndWait();
	}
	
	@SuppressWarnings("unchecked")
	private static void viewPlayers() {

		ObservableList<CAHPlayer> data = FXCollections.observableArrayList(players);
		
		Stage view = new Stage();
		TableView<CAHPlayer> table = new TableView<CAHPlayer>();
		
		TableColumn<CAHPlayer, String> playerName = new TableColumn<CAHPlayer, String>("Player");
			playerName.setMinWidth(100);
			playerName.setCellValueFactory(new PropertyValueFactory<CAHPlayer, String>("name"));
		
		TableColumn<CAHPlayer, Integer> playerScore = new TableColumn<CAHPlayer, Integer>("Score");
			playerScore.setMinWidth(100);
			playerScore.setCellValueFactory(new PropertyValueFactory<CAHPlayer, Integer>("points"));
			
		
		table.setItems(data);
		table.getColumns().addAll(playerName, playerScore);
			
		VBox vb = new VBox();
			vb.getChildren().add(table);
		
		
		if (players.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "There are no players.", ButtonType.CLOSE);
			alert.showAndWait();
		} else {
			for (CAHPlayer c : players) {
				String name = c.getName();
				
			}
			Scene scene = new Scene(vb);
			view.setScene(scene);
			view.showAndWait();
		}
	}
	
	
	private static void playCardsAgainstHumanity() {
//		players = initializePlayers();
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
	
//	private static ArrayList<CAHPlayer> initializePlayers() {
//		ArrayList<CAHPlayer> newPlayers = new ArrayList<>();
//		int numOfPlayers = ConsoleIO.promptForInt("How many players are there?: ", 0, Integer.MAX_VALUE);
//		for (int c = 0; c < numOfPlayers; c++) {
//			String name = ConsoleIO.promptForInput("What is the name of Player " + (c + 1) + "? ", false);
//			CAHPlayer p = new CAHPlayer(name);
//			newPlayers.add(p);
//		}
//		return newPlayers;
//	}
	
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