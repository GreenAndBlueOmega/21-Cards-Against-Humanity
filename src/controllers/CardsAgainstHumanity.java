package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import application.MainMenu;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.BlackCard;
import models.CAHPlayer;
import models.Card;
import models.WhiteCard;

public class CardsAgainstHumanity {

	private static ArrayList<CAHPlayer> players = new ArrayList<CAHPlayer>();
	private static ArrayList<WhiteCard> whiteDeck;
	private static ArrayList<BlackCard> blackDeck;
	private static boolean chooseCardCzar = false;
	private static boolean hasWon = false;
	private static boolean isRoundOne = true;
	private static String whiteDefault = "decks/default_white.txt";
	private static String blackDefault = "decks/default_black.txt";
	private static ArrayList<WhiteCard> defaultWhiteDeck = parseWhiteDeck(importDeckFromText(whiteDefault));
	private static ArrayList<BlackCard> defaultBlackDeck = parseBlackDeck(importDeckFromText(blackDefault));
	private static HashMap<CAHPlayer, ArrayList<WhiteCard>> meltingPot = new HashMap<CAHPlayer, ArrayList<WhiteCard>>();
	private static BlackCard roundCard;
	private static StackPane roundCardPane;
	private static int win = 2;
	private static int defaultHand = 7;
	private static Stage stage;
	private static CAHPlayer winner;
	
	public static void playCardsAgainstHumanity(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D size = screen.getVisualBounds();
		stage = primaryStage;

		if (stage == null) {
			stage = new Stage();
		} else {
			stage.setX(size.getMinX());
			stage.setY(size.getMinY());
			stage.setWidth(size.getWidth());
			stage.setHeight(size.getHeight());
		}
		
		Scene scene = mainMenu();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
	private static Button button(String text) {
		Button button = new Button(text);
			button.setStyle("-fx-font: 32 arial; -fx-base: white; -fx-text-fill: black;");
		return button;
	}
	
	private static Scene mainMenu() {
		Text title = new Text("Cards Against Humanity");
			title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 72));
			title.setFill(Color.WHITE);
		
		Text subtitle = new Text("A game for horrible people.");
			subtitle.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 36));
			subtitle.setFill(Color.WHITE);
			
		Button startGame = button("Start Game");
			startGame.setOnAction(e -> {				
				if (players.size() < 3) {
					Alert notEnough = new Alert(AlertType.ERROR, "There are not enough players.", ButtonType.OK);
						notEnough.setTitle("Not Enough Players");
						notEnough.showAndWait();
				} else {
					startGame();
				}
			}
		);
		
		Button playersButton = button("Players");
			playersButton.setOnAction(e -> stage.setScene(playersMenu()));
		
		Button settings = button("Options");
			settings.setOnAction(e -> stage.setScene(optionsMenu()));
			
		Button gameSelection = button("Return to Game Selection");
			gameSelection.setOnAction(e -> {
				Alert save = new Alert(AlertType.WARNING, "Would you like to save the current game?", ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> choice = save.showAndWait();
				
				if (choice.isPresent() && (choice.get().equals(ButtonType.YES))) {
					//save game
				}
				MainMenu m = new MainMenu();
				stage.setScene(m.mainMenu());
				
			}
		);			
		
		VBox center = new VBox();
			center.setAlignment(Pos.CENTER);
			center.getChildren().addAll(startGame, playersButton, settings);
			center.setSpacing(20);
			center.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));		
			
		VBox text = new VBox();
			text.setAlignment(Pos.CENTER);
			text.getChildren().addAll(title, subtitle);
			text.setPadding(new Insets(30, 0, 0, 0));
		
		VBox top = new VBox();
			top.getChildren().addAll(addMenuBar(), text);
			top.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		VBox bottom = new VBox();
			bottom.setAlignment(Pos.CENTER);
			bottom.setPadding(new Insets(0, 0, 150, 0));
			bottom.getChildren().add(gameSelection);
			bottom.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		BorderPane bp = new BorderPane();
			bp.setTop(top);
			bp.setCenter(center);
			bp.setBottom(bottom);
			
		return new Scene(bp);
	}
	
	private static void startGame() {
		if (isRoundOne) {
			hasWon = false;
			winner = null;
			
			checkForWhiteDeck();		
			checkForEnoughWhiteCards();		
			checkForBlackDeck();
			resetPlayerData();
			pickFirstCardCzar();
		}
		startRound();		
		isRoundOne = false;
		
		if (hasWon) {
			declareWinner();
			stage.setScene(mainMenu());
			stage.show();
		}
	}
	
	private static void resetPlayerData() {
		for (CAHPlayer p : players) {
			p.setPoints(0);
			p.setHand(null);
			p.setCardCzar(false);
		}
	}
	
	private static void declareWinner() {
		Alert announce = new Alert(AlertType.CONFIRMATION, "Congratulations, " + winner.getName() + "! You won! Returning to the main menu.", ButtonType.OK);
			announce.showAndWait();
	}
	
	private static void checkForWhiteDeck() {
		if (whiteDeck == null) {
			Alert noWhite = new Alert(AlertType.INFORMATION, "Since no white deck was imported, the default white deck will be used.", ButtonType.OK);
				noWhite.setTitle("No White Deck");
				noWhite.initOwner(stage);
				noWhite.showAndWait();
			whiteDeck = defaultWhiteDeck;
			shuffleCards(whiteDeck);
		}
	}
	
	private static void checkForEnoughWhiteCards() {
		if (players.size() * defaultHand > whiteDeck.size()) {
			Alert tooSmall = new Alert(AlertType.WARNING, "", ButtonType.OK);
				tooSmall.setTitle("Not Enough Cards. Returning to the Main Menu.");
				tooSmall.initOwner(stage);
				tooSmall.showAndWait();
			stage.setScene(mainMenu());
		}
	}
	
	private static void checkForBlackDeck() {
		if (blackDeck == null) {
			Alert noBlack = new Alert(AlertType.INFORMATION, "Since no black deck was imported, the default black deck will be used.", ButtonType.OK);
				noBlack.setTitle("No Black Deck");
				noBlack.initOwner(stage);
				noBlack.showAndWait();
			blackDeck = defaultBlackDeck;
		}
		shuffleCards(blackDeck);
		roundCard = blackDeck.get(0);
	}
	
	private static void pickFirstCardCzar() {
		if (chooseCardCzar) {
			Stage popup = new Stage();
			
			Label prompt = new Label("Which player will be the Card Czar for the first round?");
			
			ChoiceBox<CAHPlayer> cb = new ChoiceBox<CAHPlayer>();
				cb.setItems(FXCollections.observableArrayList(players));
				cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value) {
						CAHPlayer p = (players.get(new_value.intValue()));
						p.setCardCzar(true);
					}
				}
			);
				
			Button done = button("Done");
				done.setOnAction(e -> popup.close());
				
			VBox vb = new VBox();
				vb.setAlignment(Pos.CENTER);
				vb.setSpacing(20);
				vb.getChildren().addAll(prompt, cb, done);
			
			popup.setScene(new Scene(vb));
			popup.initOwner(stage);
			popup.setTitle("Pick a Card Czar");
			popup.showAndWait();
		} else {
			Random czarPicker = new Random();
				int random = czarPicker.nextInt(players.size());
				players.get(random).setCardCzar(true);
			
			Alert firstCzar = new Alert(AlertType.INFORMATION, players.get(random).getName() + " has been randomly selected to be the first Card Czar.", ButtonType.OK);
				firstCzar.showAndWait();
		}
	}
	
	private static void nextCardCzar() {		
		for (int c = 0; c < players.size(); c++) {
			if (players.get(c).isCardCzar()) {
				players.get(c).setCardCzar(false);
				
				if (c == players.size() - 1) {
					players.get(0).setCardCzar(true);
//					Alert info = new Alert(AlertType.INFORMATION, players.get(c).getName() + " is now theCard Czar.", ButtonType.OK);
//						info.setTitle("Next Card Czar");
//						info.initOwner(stage);
//						info.showAndWait();
					return;
				} else {
					players.get(c + 1).setCardCzar(true);
//					Alert info = new Alert(AlertType.INFORMATION, players.get(c + 1).getName() + " is now the Card Czar.", ButtonType.OK);
//						info.setTitle("Next Card Czar");
//						info.initOwner(stage);
//						info.showAndWait();
					return;
				}
			}
		}
	}
	
	private static void round() {
		dealWhiteCards();
		
		for (CAHPlayer p : players) {
			if (p.isCardCzar()) {
				Alert czar = new Alert(AlertType.INFORMATION, "You are the Card Czar for this round.", ButtonType.OK);
					czar.setHeaderText(p.getName() + " is the Card Czar!");
					czar.setTitle("Card Czar");
					czar.showAndWait();
			} else {
				promptForPlay(p);
			}
		}
		
		pickWinningCard();
		checkForWinner();		
		nextCardCzar();
	}
	
	private static void pickWinningCard() {
		Stage pickCard = new Stage();
		
		VBox whiteCards = new VBox();		
		
		for (Map.Entry<CAHPlayer, ArrayList<WhiteCard>> entry : meltingPot.entrySet()) {
			ArrayList<WhiteCard> playedCards = entry.getValue();
			
			
			String plural = "these cards?";
			String singular = "this card?";
			
			for (WhiteCard c : playedCards) {
				Button card = button(c.getText());
				whiteCards.getChildren().add(card);
					card.setOnAction(e -> {
						String checkPlural = "";
						CAHPlayer player = entry.getKey();
						
						if (playedCards.size() > 1) {
							checkPlural = plural;
						} else {
							checkPlural = singular;
						}

						Alert chosen = new Alert(AlertType.CONFIRMATION, "Are you sure you want to select " + checkPlural, ButtonType.YES, ButtonType.NO);
							Optional<ButtonType> result = chosen.showAndWait();
								if (result.isPresent() && result.get() == ButtonType.YES) {
									int currentPoints = player.getPoints();
									player.setPoints(currentPoints + 1);
									meltingPot.clear();
									pickCard.close();
								} else {
									
								}
						Alert inform = new Alert(AlertType.INFORMATION, player.getName() + " has won this round!", ButtonType.OK);
							inform.showAndWait();
					}
				);
			}
		}
		
			whiteCards.setAlignment(Pos.CENTER);
			whiteCards.setSpacing(20);
			whiteCards.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		
		BorderPane bp = new BorderPane();
			bp.setTop(roundCardPane);
			bp.setCenter(whiteCards);
			
		pickCard.setTitle("Melting Pot");
		pickCard.setScene(new Scene(bp));
		pickCard.initOwner(stage);
		pickCard.showAndWait();
	}
	
	private static void checkForWinner() {
		for (CAHPlayer p : players) {
			if (p.getPoints() >= win) {
				hasWon = true;
				winner = p;
			}
			
			if (hasWon) {
				Alert won = new Alert(AlertType.INFORMATION, winner.getName() + " has won!\nReturning to the Main Menu.", ButtonType.OK);
					won.setTitle("Game Over");
					won.setHeaderText("Congratulations!");
					won.showAndWait();
				stage.setScene(mainMenu());
				isRoundOne = true;
				return;
			}
		}
		
	}
	
	private static void promptForPlay(CAHPlayer p) {
		Label prompt = new Label("Select a Card to Play");
			prompt.setStyle("-fx-font-size: 36; -fx-text-fill: white; -fx-font-weight: bold;");
			
		VBox top = new VBox();
			top.setAlignment(Pos.CENTER);
			top.getChildren().add(prompt);
			top.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		VBox buttons = new VBox();
			buttons.setAlignment(Pos.CENTER_LEFT);
			buttons.setSpacing(20);
		
		Stage popup = new Stage();
		
		for (int i = 0; i < roundCard.getPlay(); i++) {	

			Stage popup2 = new Stage();
			
			for (int c = 0; c < p.getHand().size(); c++) {
				ArrayList<WhiteCard> hand = p.getHand();
				Button card = button(hand.get(c).getText());
				int index = c;
				int difference = i;
					card.setOnAction(e -> {
						Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to play this card?", ButtonType.YES, ButtonType.NO);
						Optional<ButtonType> result = confirm.showAndWait();
						if (result.isPresent() && result.get() == ButtonType.YES) {
							popup.close();
							playCard(p, index - difference);	
						}						
					}
				);
				if (i == 0) {					
					buttons.getChildren().add(card);	
				} else if (i == 1) {					
					popup2.setScene(popup.getScene());
				}
			}
		}
		
		buttons.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		
		BorderPane bp = new BorderPane();
			bp.setTop(top);
			bp.setCenter(buttons);
			
			popup.setTitle(p.getName() + "'s Cards");
			popup.setScene(new Scene(bp));
			popup.showAndWait();
	}
	
	private static void playCard(CAHPlayer p, int index) {
		for (int c = 0; c < roundCard.getPlay(); c++) {
			ArrayList<WhiteCard> hand = p.getHand();
			ArrayList<WhiteCard> hashMapHand = new ArrayList<WhiteCard>();
				hashMapHand.add(hand.get(index));
			meltingPot.put(p, hashMapHand);
			hand.remove(index);
			p.setHand(hand);
		}
	}
	
	private static void startRound() {	
		roundCard = blackDeck.get(0);
		
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER_LEFT);
			vb.setPadding(new Insets(0, 30, 0, 0));
		
		for (int c = 0; c < players.size(); c++) {
			Button name = new Button((c + 1) + ". " + players.get(c).getName());
				name.setMinWidth(200);
				name.setMaxWidth(200);
				name.setStyle("-fx-font-family: arial; -fx-font-size: 24;");
				int index = c;
				name.setOnAction(e -> {
					String playerName = players.get(index).getName();
					String info = playerName + " currently has " + players.get(index).getPoints() + " points.\n" + playerName + 
								" is " + (players.get(index).isCardCzar() ? "" : "not ") + "the current Card Czar.";
					Alert playerInfo = new Alert(AlertType.INFORMATION, info, ButtonType.OK);
						playerInfo.setHeaderText(playerName);
						playerInfo.setTitle("Player Info: " + playerName);
						playerInfo.showAndWait();
				}
			);
			vb.getChildren().add(name);
		}
		Button viewHand = button("View Hand");
			viewHand.setOnAction(e -> {
				for (CAHPlayer p : players) {
					viewPlayerHand(p);					
				}
			}
		);
			
		Button nextRound = button("Next Round");
			nextRound.setOnAction(e -> round());
			
		Button mainMenu = button("Return to Main Menu");
			mainMenu.setOnAction(e -> stage.setScene(mainMenu()));
		
		Button next = button("New Card");
			next.setOnAction(e -> startRound());
			
		VBox right = new VBox();
			right.setAlignment(Pos.CENTER);
			
			int d = roundCard.getDraw();
			int p = roundCard.getPlay();
			Label draw = new Label("Draw: " + d);
				draw.setStyle("-fx-font-size: 24;");
			Label play= new Label("Play: " + p);
				play.setStyle("-fx-font-size: 24;");
			
			right.getChildren().addAll(draw, play);
			
			
			
		HBox buttons = new HBox();
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(20);
			buttons.getChildren().addAll(viewHand, mainMenu, nextRound, next);

		BorderPane bp = new BorderPane();
			bp.setTop(addMenuBar());
			bp.setLeft(vb);
			bp.setCenter(dealBlackCard());
			bp.setBottom(buttons);
			bp.setRight(right);
		
		stage.setScene(new Scene(bp));
		stage.show();
	}
	
	private static void dealWhiteCards() {
		if (isRoundOne) {
			for (int count = 0; count < defaultHand; count++) {
				for (int c = 0; c < players.size(); c++) {
					ArrayList<WhiteCard> tempHand = players.get(c).getHand();
					
					if (tempHand == null) {
						tempHand = new ArrayList<WhiteCard>();
					}
					
					tempHand.add(whiteDeck.get(0));
					players.get(c).setHand(tempHand);
					whiteDeck.remove(0);
				}
			}			
		} else {
			for (int c = 0; c < players.size(); c++) {
				ArrayList<WhiteCard> tempHand = players.get(c).getHand();
				
				if (tempHand == null) {
					tempHand = new ArrayList<WhiteCard>();
				}
				
				while (tempHand.size() < 7) {
					tempHand.add(whiteDeck.get(0));
					players.get(c).setHand(tempHand);
					whiteDeck.remove(0);
				}
			}
		}
	}
	
	private static void viewPlayerHand(CAHPlayer p) {		
		ArrayList<WhiteCard> hand = p.getHand();
		
		VBox vb = new VBox();
		
		int count = 1;
		
		for (WhiteCard c : hand) {
			Label card = new Label(count + ". " + c.getText());	
			vb.getChildren().add(card);
			count++;
		}
		
		Stage popup = new Stage();
		popup.setScene(new Scene(vb));
		popup.setTitle(p.getName() + "'s Cards");
		popup.showAndWait();
	}
	
	private static StackPane dealBlackCard() {		
		ImageView background = new ImageView(new Image("file:images/blankblack.png"));
		
		roundCard = blackDeck.get(0);
		
		String value = roundCard.getText();
		
		List<String> words = Arrays.asList(value.split(" "));		
		
		StringBuilder sb = new StringBuilder();
		
		int counter = 0;
		for (int c = 0; c < words.size(); c++) {
			String temp = words.get(c) + " ";			
			
			if (c != 0 && counter % 6 == 0) {
				temp += "\n";
			} else if (words.get(c).contains(".") || words.get(c).contains(",")) {
				temp += "\n";
				counter = 0;
			}
			
			sb.append(temp);
			
			counter++;
		}
		
		
		Text prompt = new Text(sb.toString());
			prompt.setStyle("-fx-font-size: 16;");
			prompt.setTextAlignment(TextAlignment.CENTER);
			prompt.setFill(Color.WHITE);
		
		StackPane dealtCard = new StackPane();
			dealtCard.getChildren().add(background);
			dealtCard.getChildren().add(prompt);
			dealtCard.setAlignment(Pos.CENTER);
		
		roundCardPane = dealtCard;
		blackDeck.remove(0);
		return dealtCard;
	}
	
	private static Scene playersMenu() {
		Button addButton = button("Add Players");
			addButton.setOnAction(e -> stage.setScene(addPlayers()));
		
		Button removeButton = button("Remove Players");
			removeButton.setOnAction(e -> stage.setScene(removePlayers()));
		
		Button viewButton = button("View All Players");
			viewButton.setOnAction(e -> viewPlayers());
			
		Button mainMenu = button("Return to Main Menu");
			mainMenu.setOnAction(e -> stage.setScene(mainMenu()));
		
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.setSpacing(30);
			vb.getChildren().addAll(addButton, removeButton, viewButton, mainMenu);
			
		BorderPane bp = new BorderPane();
			bp.setTop(addMenuBar());
			bp.setCenter(vb);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		return new Scene(bp);
	}
	
	private static Scene addPlayers() {
		Text title = new Text("Add Players");
			title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 72));
			title.setFill(Color.WHITE);
		Label prompt = new Label("Enter the name of the player: ");
			prompt.setStyle("-fx-font-family: arial; -fx-text-fill: white;");
		TextField userInput = new TextField();
			userInput.setMaxWidth(400);
			userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent key) {
					if (key.getCode().equals(KeyCode.ENTER)) {
						String name = userInput.getText();
						CAHPlayer c = new CAHPlayer(name);
						players.add(c);
						Alert a = new Alert(AlertType.INFORMATION, name + " has been added.", ButtonType.OK);
							a.setHeaderText("Player Added");
							a.setTitle("Add a Player");
							a.showAndWait();
						userInput.clear();
						userInput.requestFocus();
					}
				}
			}
		);
		Button add = new Button("Add Player");
			add.setOnAction(e -> {
				String name = userInput.getText();
				CAHPlayer c = new CAHPlayer(name);
				players.add(c);
				Alert a = new Alert(AlertType.INFORMATION, name + " has been added.", ButtonType.OK);
					a.showAndWait();
				userInput.clear();
				userInput.requestFocus();
			}
		);
		
		Button donezo = new Button("Return to Player Menu");
			donezo.setOnAction(e -> stage.setScene(playersMenu()));
			
		VBox top = new VBox();
			top.setAlignment(Pos.CENTER);
			top.getChildren().addAll(addMenuBar(), title);
			top.setSpacing(40);
			
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.setSpacing(20);
			vb.getChildren().addAll(prompt, userInput, add, donezo);
			vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		BorderPane bp = new BorderPane();
			bp.setTop(top);
			bp.setCenter(vb);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		Scene scene = new Scene(bp);
			scene.getRoot();
			
		return scene;
	}
	
	private static Scene removePlayers() {		
		VBox vb = new VBox();
			vb.setSpacing(20);
			vb.setAlignment(Pos.CENTER);
			vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		Text title = new Text("Remove Players");
			title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 72));
			title.setFill(Color.WHITE);
		
		Label prompt = new Label("Enter the name of the player: ");
			prompt.setStyle("-fx-font-family: arial; -fx-text-fill: white;"); 
		
		TextField userInput = new TextField();
			userInput.setMaxWidth(400);
			userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent key) {
					if (key.getCode().equals(KeyCode.ENTER)) {
						String tempName = userInput.getText();
						int counter = 0;
						int match = -1;
						
						for (CAHPlayer p : players) {
							if (tempName.equals(p.getName())) {
								Alert confirm = new Alert(AlertType.WARNING, "Are you sure you want to remove " + tempName + "?", ButtonType.YES, ButtonType.NO);
								Optional<ButtonType> result = confirm.showAndWait();
								
								if (result.isPresent() && (result.get() == ButtonType.YES)) {
									match = counter;
								}
							}
							counter++;
						}
						
						
						if (match == -1) {
							Alert inform = new Alert(AlertType.INFORMATION, "There is no player named " + tempName + ".", ButtonType.OK);
							inform.showAndWait();
						} else {
							players.remove(match);
						}
						
						userInput.clear();
						userInput.requestFocus();
					}
				}
			}
		);
			
		String name = userInput.getText();
			
		Button remove = new Button("Remove Player");
			remove.setOnAction(e -> {
				String tempName = userInput.getText();
				int counter = 0;
				int match = -1;
				
				for (CAHPlayer p : players) {
					if (tempName.equals(p.getName())) {
						Alert confirm = new Alert(AlertType.WARNING, "Are you sure you want to remove " + tempName + "?", ButtonType.YES, ButtonType.NO);
						Optional<ButtonType> result = confirm.showAndWait();
						
						if (result.isPresent() && (result.get() == ButtonType.YES)) {
							match = counter;
						}
					}
					counter++;
				}
				
				
				if (match == -1) {
					Alert inform = new Alert(AlertType.INFORMATION, "There is no player named " + tempName + ".", ButtonType.OK);
					inform.showAndWait();
				} else {
					players.remove(match);
				}
				
			}
		);
		Button done = new Button("Return to Player Menu");	
			done.setOnAction(e -> stage.setScene(playersMenu()));
		

		
			vb.getChildren().add(title);
			
		for (CAHPlayer p : players) {
			Label l = new Label(p.getName());
				l.setStyle("-fx-text-fill: white; -fx-font-size: 18;");
			vb.getChildren().add(l);
		}
		
		for (CAHPlayer p : players) {
			String tempName = p.getName();
			if (tempName.equals(name)) {
				players.remove(p);
			}
		}
		
		VBox buttons = new VBox();
			buttons.setAlignment(Pos.CENTER);
			buttons.setPadding(new Insets(20, 20, 20, 20));
			buttons.setSpacing(20);
			buttons.getChildren().addAll(remove, done);

			vb.getChildren().addAll(prompt, userInput, buttons);
		
		VBox titles = new VBox();
			titles.setAlignment(Pos.CENTER);
			titles.setSpacing(40);
			titles.getChildren().addAll(addMenuBar(), title);
			
		BorderPane bp = new BorderPane();
			bp.setTop(titles);
			bp.setCenter(vb);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		return new Scene(bp);
	}
	
	@SuppressWarnings("all")
	private static void viewPlayers() {
		
		Stage popup = new Stage();
		
		final ObservableList<CAHPlayer> data = FXCollections.observableArrayList(players);
		
		TableView tablo = new TableView();
		
		TableColumn<String, Integer> indexColumn = new TableColumn<>("#");
			indexColumn.setStyle("-fx-alignment: CENTER;");
			indexColumn.setCellFactory(col -> {
				TableCell<String, Integer> indexCell = new TableCell<>();
				ReadOnlyObjectProperty<TableRow> rowProperty = indexCell.tableRowProperty();
				ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
					TableRow<String> row = rowProperty.get();
					if (row != null) {
						int rowIndex = row.getIndex();
						if (rowIndex < row.getTableView().getItems().size()) {
							return Integer.toString(rowIndex + 1);
						}	
					}	
					return null;
				}, rowProperty);
					indexCell.textProperty().bind(rowBinding);
					return indexCell;
			}
		);
		TableColumn playerName = new TableColumn("Name");
			playerName.setStyle("-fx-alignment: CENTER;");
			playerName.setCellValueFactory(new PropertyValueFactory<CAHPlayer, String>("name"));
		TableColumn score = new TableColumn("Score");
			score.setStyle("-fx-alignment: CENTER;");
			score.setCellValueFactory(new PropertyValueFactory<CAHPlayer, Integer>("points"));
			
		tablo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		tablo.getColumns().addAll(indexColumn, playerName, score);
		tablo.setItems(data);
		
		Button close = new Button("Close");
			close.setOnAction(e -> popup.close());
		
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(tablo, close);
			vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			vb.setMinWidth(600);
		
		popup.setScene(new Scene(vb));
		popup.initOwner(stage);
		popup.getIcons().add(stage.getIcons().get(0));
		popup.setTitle("Players");
		popup.setResizable(false);
		popup.showAndWait();
	}
	
	private static MenuBar addMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
			MenuItem fileFullscreen = new MenuItem("Toggle Fullscreen");
				fileFullscreen.setOnAction(e -> stage.setFullScreen(stage.isFullScreen() ? false: true));
			MenuItem fileExit = new MenuItem("Exit");
				fileExit.setOnAction(e -> Platform.exit());
		
			fileMenu.getItems().addAll(fileFullscreen, fileExit);
		
		menuBar.getMenus().add(fileMenu);
		
		return menuBar;
	}
	
	private static Scene optionsMenu() {
		Text title = new Text("Options");
			title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 72));
			title.setFill(Color.WHITE);
		
		Button white = button("White Deck Settings");
			white.setOnAction(e -> stage.setScene(whiteDeckSettings()));
		
		Button black = button("Black Deck Settings");
			black.setOnAction(e -> stage.setScene(blackDeckSettings()));
		
		ToggleGroup cardCzar = new ToggleGroup();
		
		RadioButton cardCzarDefault = new RadioButton("Randomly Pick First Card Czar");
			cardCzarDefault.setStyle("-fx-font: 32 arial; -fx-base: white; -fx-text-fill: white;");
			cardCzarDefault.setToggleGroup(cardCzar);
			cardCzarDefault.setSelected(true);
			cardCzarDefault.requestFocus();
			cardCzarDefault.setOnAction(e -> {
				chooseCardCzar = false;
				Alert alert = new Alert(AlertType.INFORMATION, "The first Card Czar will be chosen randomly.", ButtonType.OK);
				alert.showAndWait();
			}
		);
		
		RadioButton cardCzarChoice = new RadioButton("Manually Pick First Card Czar");
			cardCzarChoice.setStyle("-fx-font: 32 arial; -fx-base: white; -fx-text-fill: white;");
			cardCzarChoice.setToggleGroup(cardCzar);
			cardCzarChoice.setOnAction(e -> {
				chooseCardCzar = true;
				Alert alert = new Alert(AlertType.INFORMATION, "The first Card Czar will be chosen manually at the start of the game.", ButtonType.OK);
				alert.showAndWait();
			}
		);
			
		Button menu = button("Return to Main Menu");
			menu.setOnAction(e -> stage.setScene(mainMenu()));
			
		HBox radios = new HBox();	
			radios.setAlignment(Pos.CENTER);
			radios.setSpacing(20);
			radios.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			radios.getChildren().addAll(cardCzarDefault, cardCzarChoice);
			
		VBox center = new VBox();
			center.setAlignment(Pos.CENTER);
			center.setSpacing(20);
			center.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			center.getChildren().addAll(white, black, radios);
			
		VBox top = new VBox();
			top.setAlignment(Pos.CENTER);
			top.setSpacing(40);
			top.getChildren().addAll(addMenuBar(), title);
			
		VBox bottom = new VBox();
			bottom.setAlignment(Pos.CENTER);
			bottom.getChildren().add(menu);
			bottom.setPadding(new Insets(0, 0, 150, 0));
			
		BorderPane bp = new BorderPane();
			bp.setTop(top);
			bp.setCenter(center);
			bp.setBottom(bottom);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		return new Scene(bp);
	}
	
	private static Scene blackDeckSettings() {
		Button useDefault = button("Use Default Black Deck");
			useDefault.setOnAction(e -> {
				Alert a = new Alert(AlertType.INFORMATION, "The default black deck will be used.", ButtonType.OK);
				a.setHeaderText("Attention!");
				a.setTitle("Default Black Deck");
				a.initOwner(stage);
				a.showAndWait();
				blackDeck = defaultBlackDeck;
			}
		);			
	
		Button importDeck = button("Import an Existing Deck");
			importDeck.setOnAction(e -> importPopup(true));
		
		Button createDeck = button("Create a New Black Deck");
		
		Button viewDeck = button("View Cards in Deck");
			viewDeck.setOnAction(e -> viewDeck(blackDeck, "black"));
	
		Button options = button("Return to Options Menu");
			options.setOnAction(e -> stage.setScene(optionsMenu()));
	
		Button mainMenu = button("Return to Main Menu");
			mainMenu.setOnAction(e -> stage.setScene(mainMenu()));
	
		VBox buttons = new VBox();
			buttons.setSpacing(20);
			buttons.setAlignment(Pos.CENTER);
			buttons.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			buttons.getChildren().addAll(useDefault, importDeck, createDeck, viewDeck, options, mainMenu);
		
		
		BorderPane bp = new BorderPane();
			bp.setTop(addMenuBar());
			bp.setCenter(buttons);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		
	return new Scene(bp);
	}
	
	private static Scene whiteDeckSettings() {
		Button useDefault = button("Use Default White Deck");
			useDefault.setOnAction(e -> {
				Alert a = new Alert(AlertType.INFORMATION, "The default white deck will be used.", ButtonType.OK);
					a.setHeaderText("Attention!");
					a.setTitle("Default White Deck");
					a.initOwner(stage);
					a.showAndWait();
				whiteDeck = defaultWhiteDeck;
			}
		);			
		
		Button importDeck = button("Import an Existing Deck");
			importDeck.setOnAction(e -> {
				importPopup(false);
			}
		);
			
		Button createDeck = button("Create a New White Deck");
			createDeck.setOnAction(e -> createWhiteCards());
			
		Button viewDeck = button("View Cards in Deck");
			viewDeck.setOnAction(e -> viewDeck(whiteDeck, "white"));
		
		Button options = button("Return to Options Menu");
			options.setOnAction(e -> stage.setScene(optionsMenu()));
		
		Button mainMenu = button("Return to Main Menu");
			mainMenu.setOnAction(e -> stage.setScene(mainMenu()));
		
		VBox buttons = new VBox();
			buttons.setSpacing(20);
			buttons.setAlignment(Pos.CENTER);
			buttons.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			buttons.getChildren().addAll(useDefault, importDeck, createDeck, viewDeck, options, mainMenu);
		
		BorderPane bp = new BorderPane();
			bp.setTop(addMenuBar());
			bp.setCenter(buttons);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		return new Scene(bp);
	}
	
	private static void createWhiteCards() {
		Stage create = new Stage();
		
		ArrayList<WhiteCard> tempDeck = new ArrayList<WhiteCard>();
		
		Label prompt = new Label("Enter the text for the white card: ");
		
		TextField userInput = new TextField();
			userInput.setMaxWidth(400);
		
		Button add = button("Create Card");
			add.setOnAction(e -> {
				String text = userInput.getText();
				WhiteCard c = new WhiteCard(text);
				tempDeck.add(c);
			}
		);
			
		Button view = button("View Current Cards");
			view.setOnAction(e -> {
				VBox vb = new VBox();
					vb.setAlignment(Pos.CENTER);
					
				for (WhiteCard c : tempDeck) {
					Label label = new Label(c.getText());
					vb.getChildren().add(label);
				}
				
				Stage popup = new Stage();
				popup.setScene(new Scene(vb));
				popup.showAndWait();
			}
		);
		
		HBox buttons = new HBox();
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(20);
		
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(prompt, userInput, buttons);
		
		create.showAndWait();
	}
	
	private static void importPopup(boolean isBlack) {		
		Stage popup = new Stage();
		
		Label prompt = new Label("Enter the filepath of the deck: ");
		
		TextField userInput = new TextField();
			userInput.setMaxWidth(400);
			userInput.setOnKeyPressed(k -> {
				if (k.getCode().equals(KeyCode.ENTER)) {
					if (isBlack) {
						ArrayList<BlackCard> tempDeck = new ArrayList<BlackCard>();
						String path = userInput.getText();
						String[] cards = importDeckFromText(path);
						
						if (cards != null) {
							int draw = -1;
							int play = -1;
							for (String s : cards) {
								
								if (s.contains("________")) {
									int times = StringUtils.countMatches(s, "________");
									play = times;
									draw = play > 1 ? play - 1 : 0;
								} else if (s.contains("?")) {
									draw = 0;
									play = 1;
									
								} else {
									Stage prompts = new Stage();
										prompts.initModality(Modality.APPLICATION_MODAL);
										prompts.setTitle("Create a Black Card");
										
									Label promptDraw = new Label("What is the \"Draw\" value of this card?");
									Label promptPlay = new Label("What is the \"Play\" value of this card?");
									
									ObservableList<Integer> options = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7);
									
									final ComboBox<Integer> userDraw = new ComboBox<Integer>(options);
										userDraw.setVisibleRowCount(3);
										
									final ComboBox<Integer> userPlay = new ComboBox<Integer>(options);
										userPlay.setVisibleRowCount(3);
									
									HBox drawBox = new HBox();
										drawBox.setAlignment(Pos.CENTER);
										drawBox.setSpacing(20);
										drawBox.getChildren().addAll(promptDraw, userDraw);
										
									HBox playBox = new HBox();
										playBox.setAlignment(Pos.CENTER);
										playBox.setSpacing(20);
										playBox.getChildren().addAll(promptPlay, userPlay);
									
									VBox inputVB = new VBox();
										inputVB.setAlignment(Pos.CENTER);
										inputVB.setSpacing(20);
										inputVB.getChildren().addAll(drawBox, playBox);
										
									prompts.setScene(new Scene(inputVB));
									prompts.showAndWait();
									draw = userDraw.getValue();
									play = userPlay.getValue();
									
								}
								
								BlackCard c = new BlackCard(s, draw, play);
								tempDeck.add(c);
							}
							blackDeck = tempDeck;
						}
						
					} else {
						ArrayList<WhiteCard> tempDeck = new ArrayList<WhiteCard>();
						String path = userInput.getText();
						String[] cards = importDeckFromText(path);
					
						if (cards != null) {
							for (String s : cards) {
								WhiteCard c = new WhiteCard(s);
								tempDeck.add(c);
							}
							whiteDeck = tempDeck;
						}
					}
				}
			}
		);
			
		Button importButton = button("Import");
			importButton.setOnAction(e -> {
				String path = userInput.getText();
				String[] cards = importDeckFromText(path);
				
				if (cards == null) {
					
				} else {
					for (String s : cards) {
						System.out.println(s);
					}
				}
			}
		);
		
		Button finish = button("Done");
			finish.setOnAction(e -> popup.close());
			
		HBox buttons = new HBox();
			buttons.setAlignment(Pos.CENTER);
			buttons.getChildren().addAll(importButton, finish);
			buttons.setSpacing(20);
			
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(prompt, userInput, buttons);
			vb.setSpacing(20);
			
		BorderPane bp = new BorderPane();
			bp.setCenter(vb);
			
		popup.setScene(new Scene(bp));
		popup.setTitle("Import White Deck");
			
		popup.showAndWait();
	}
	
	private static String[] importDeckFromText(String path) {	
		String[] cards = null;
		StringBuilder parsedString = new StringBuilder();
		
		if (path.equalsIgnoreCase("white")) {
			path = whiteDefault;
		} else if (path.equalsIgnoreCase("black")) {
			path = blackDefault;
		}
		
		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
			stream.forEach(s -> parsedString.append(s).append("\n"));
		} catch (FileNotFoundException fnfe) {
			Alert alert = new Alert(AlertType.ERROR, "A deck file was not found at [ " + path + " ].", ButtonType.OK);
				alert.showAndWait();
		} catch (IOException ioe) {
			Alert io = new Alert(AlertType.ERROR, "The file could not be read due to some unknown error.", ButtonType.OK);
				io.showAndWait();
		} catch (NullPointerException npe) {
			Alert nullPoint = new Alert(AlertType.ERROR, "Caught NullPointerError at importDeckFromText\nPath: " + path, ButtonType.OK);
				nullPoint.showAndWait();
		}
		
		String temp = parsedString.toString();
		
		cards = temp.split("\\n");
		
		return cards;
	}
	
	private static ArrayList<WhiteCard> parseWhiteDeck(String[] cards) {
		ArrayList<WhiteCard> deck = new ArrayList<WhiteCard>();
		
		
		if (cards != null) {
			for (String s : cards) {
				WhiteCard wc = new WhiteCard(s);
				deck.add(wc);
			}
		}
		
		return deck;
	}
	
	private static ArrayList<BlackCard> parseBlackDeck(String[] cards) {
		ArrayList<BlackCard> deck = new ArrayList<BlackCard>();
		
		if (cards == null) {
			Alert a = new Alert(AlertType.ERROR, "Caught at parseBlackDeck method.", ButtonType.OK);
				a.showAndWait();
		} else {
			int draw = -1;
			int play = -1;
			for (String s : cards) {
				if (s.contains("________")) {
					int times = StringUtils.countMatches(s, "________");
					play = times;
					draw = play > 1 ? play - 1 : 0;
				} else if (s.contains("?")) {
					draw = 0;
					play = 1;

				} else {

				}
				BlackCard b = new BlackCard(s, draw, play);
				deck.add(b);
			}
		}
		return deck;
	}
		
	private static void exportWhiteDeck(ArrayList<WhiteCard> deck) {
		Label prompt = new Label("Enter the location to save the white deck: ");
		Label label = new Label("Enter the name of the file: ");
		TextField userInput = new TextField();
			userInput.setMaxWidth(400);
		TextField userName = new TextField();
			userName.setMaxWidth(400);
			
		Button getpath = button("Export");
			getpath.setOnAction(e -> {
				String path = "[empty]";
				String name = "[no name]";
				try {
					path = userInput.getText();
					name = userName.getText();
					File file = new File(path + "\\" + name + ".wdeck");
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream out = new ObjectOutputStream(fos);
					out.writeObject(deck);
					out.close();
					fos.close();
				} catch (IOException ioe) {
					Alert a = new Alert(AlertType.ERROR, "io exception\n" + path, ButtonType.OK);
						a.showAndWait();
				}
				
			}
		);
			
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.setSpacing(20);
			vb.getChildren().addAll(prompt, userInput, label, userName, getpath);
			
		Stage popup = new Stage();
		popup.setScene(new Scene(vb));
		popup.show();
	}
	
	private static void viewDeck(ArrayList<? extends Card> deck, String color) {
		color = color.substring(0, 1).toUpperCase() + color.substring(1).toLowerCase();
		
		VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER_LEFT);
			vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
		
		for (int c = 0; c < deck.size(); c++) {
			Label label = new Label((c + 1) + ". " + deck.get(c).getText());
				label.setTextFill(Color.WHITE);
			vb.getChildren().add(label);
		}
				
		Text title = new Text(color + " Cards");
			title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 36));
			title.setFill(Color.WHITE);
		
		VBox text = new VBox();
			text.setAlignment(Pos.CENTER);
			text.getChildren().add(title);
		
		ScrollPane sp = new ScrollPane();
			sp.setContent(vb);
			sp.setHbarPolicy(ScrollBarPolicy.NEVER);
			sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			sp.setFitToWidth(true);
			sp.setFitToHeight(true);
			
		BorderPane bp = new BorderPane();
			bp.setTop(text);
			bp.setCenter(sp);
			bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
			
		Stage popup = new Stage();
		
		popup.setScene(new Scene(bp));
		popup.setTitle(color + " Cards");
		popup.getIcons().add(new Image("file:images/icon.png"));
		popup.showAndWait();
	}
	
	private static void shuffleCards(ArrayList<? extends Card> deck) {
		for (int c = 0; c < 7; c++) {
			Collections.shuffle(deck);
		}
	}
}