package view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.jpa.PersistenceProvider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Dipendente;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		 // Crea l'EntityManagerFactory
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		primaryStage.setTitle("SAE24");
		Scene scene = new Scene(root);
		
		//Image icon = new Image("img/logo.jpg");
		//primaryStage.getIcons().add(icon);
		
		String css = this.getClass().getResource("/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
