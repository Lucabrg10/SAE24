package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.entity.Reparto;
import model.service.ManagerService;

public class Main extends Application {

	final static String USER_ADMIN = "admin";

	@Override
	public void start(Stage primaryStage) throws Exception {

		ManagerService service = new ManagerService("");
		service.addDipendente(USER_ADMIN, USER_ADMIN, USER_ADMIN, Reparto.MANAGER);
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		primaryStage.setTitle("SAE24");
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);

		// per centrare lo stage
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
