package bjGui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.applet.Main;
import bjGui.GroundWork;

public class FirstScreen {

	private Label BJ = new Label();
	private Button hit = new Button("Hit");
	private boolean takeHit = true;
	private boolean willStand = false;
	private Button stand = new Button("Stand");
	
	
	public static void holdMethods() {
		try {
//			MainScreen.setTitle("BlackJack");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void PlayBlackJack(ActionEvent event) {
		AnchorPane idk = new AnchorPane();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(Main.class.getResource("/MainScreen.fxml"));
		
	try {
		idk = load.load();
	}catch (IOException e) {
		e.printStackTrace();
	}
	Scene bj = new Scene(idk);
	Stage BlackJack = GroundWork.getBjStage();
	BlackJack.setScene(bj);
	BlackJack.show();
	}


	

	


	
}
