package application;

import java.util.ArrayList;

import controllers.Blackjack;
import controllers.CardsAgainstHumanity;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Application{
    private Stage stage;
    public ArrayList<String> players = new ArrayList<String>();
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Scene scene = mainMenu();
		stage.setScene(scene);
		stage.getIcons().add(new Image("file:images/icon.png"));
		stage.setTitle("21 Cards Against Humanity");
		stage.setMaximized(true);
		stage.show();
	}
    
    public Scene mainMenu() {
    	MenuBar menuBar = new MenuBar();
    		Menu fileMenu = new Menu("File");
    			MenuItem fullscreen = new MenuItem("Toggle Fullscreen");
    				fullscreen.setOnAction(e -> stage.setFullScreen(stage.isFullScreen() ? false : true));
    			MenuItem exit = new MenuItem("Exit");
    				exit.setOnAction(e -> Platform.exit());
    		fileMenu.getItems().addAll(fullscreen, exit);
    	menuBar.getMenus().add(fileMenu);
    	
    	Button cardsButton = new Button("Play Cards Against Humanity");
    		cardsButton.setOnAction(e -> CardsAgainstHumanity.playCardsAgainstHumanity(stage));
    			
    		
    	Button blackjackButton = new Button("Play Blackjack");
    		blackjackButton.setOnAction(e -> Blackjack.playBlackjack());
    	
    	VBox vb = new VBox();
    		vb.setAlignment(Pos.CENTER);
    		vb.getChildren().addAll(cardsButton, blackjackButton);
    		vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
    		
    	BorderPane bp = new BorderPane();
    		bp.setTop(menuBar);
    		bp.setCenter(vb);
    		bp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0))));
    	
        return new Scene(bp);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}