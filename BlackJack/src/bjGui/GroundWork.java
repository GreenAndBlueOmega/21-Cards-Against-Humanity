package bjGui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.applet.Main;

public class GroundWork extends Application {

	private static Stage bjStage;
	
	public void start(Stage primaryStage) {
		
		FirstScreen.holdMethods();
	}
	
	public static void main(String[] args) {
	launch(args);
	
	}
	private void setbjStage(Stage primaryStage) {
		this.bjStage = primaryStage;
	}

	public static Stage getBjStage() {
		return bjStage;
	}

	
}

