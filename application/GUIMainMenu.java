package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIMainMenu extends Application {
	Stage window = new Stage();

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		Parent menu = FXMLLoader.load(getClass().getResource("GUIMainMenu.fxml"));
		
		window.setTitle("21 Cards Against Humanity");
		window.setScene(new Scene(menu));
		window.setMaximized(true);
		window.setResizable(true);
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
