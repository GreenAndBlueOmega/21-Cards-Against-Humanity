package application;

import controllers.Blackjack;
import controllers.CardsAgainstHumanity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMainMenu extends Application {
	
	public void cardsAgainstHumanityClicked() {
		CardsAgainstHumanity.cardsMainMenu();
	}
	
	public void blackjackClicked() {
		Blackjack.playBlackjack();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GUIMainMenu.fxml"));
		primaryStage.setTitle("21 Cards Against Humanity");
		primaryStage.setScene(new Scene(root, 1000, 800));
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
