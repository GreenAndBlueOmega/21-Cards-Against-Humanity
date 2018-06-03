package application;

import java.util.ArrayList;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CAHMainMenu extends Application{
    private Stage stage;
    public ArrayList<String> players = new ArrayList<String>();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Scene scene = mainMenu();
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    public Scene mainMenu() {
    	// Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        
        // Create MenuItems
        MenuItem newItem = new MenuItem("New");
        MenuItem openFileItem = new MenuItem("Open File");
        MenuItem exitItem = new MenuItem("Exit");
        	exitItem.setOnAction(e -> Platform.exit());
        
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, openFileItem, exitItem);
        editMenu.getItems().addAll(copyItem, pasteItem);
        
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        
        
    	BorderPane root = new BorderPane();
    	
    	Button cardsButton = new Button("Play Cards Against Humanity");
    		cardsButton.setOnAction(e -> CardsAgainstHumanity.cardsMainMenu(stage));
    		
    	Button blackjackButton = new Button("Play Blackjack");
    		blackjackButton.setOnAction(new EventHandler<ActionEvent>() {
    			public void handle(ActionEvent t) {
    				
    			}
    		}
    	);
    	
    	VBox vb = new VBox();
    		vb.setAlignment(Pos.CENTER);
    		vb.getChildren().addAll(cardsButton, blackjackButton);
    	root.setTop(menuBar);
    	root.setCenter(vb);
        return new Scene(root);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}