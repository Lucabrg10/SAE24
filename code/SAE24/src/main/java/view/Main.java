package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
		primaryStage.setTitle("SAE24");
		Scene scene = new Scene(root);
		
		//Image icon = new Image("img/logo.jpg");
		//primaryStage.getIcons().add(icon);
		
		String css = this.getClass().getResource("/application.css").toExternalForm();
		scene.getStylesheets().add(css);//
		

		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
