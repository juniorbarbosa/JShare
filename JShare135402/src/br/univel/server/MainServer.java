package br.univel.server;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class MainServer extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainServer.fxml"));
			Scene scene = new Scene(root, 400, 300);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Servidor");
			primaryStage.show();
			primaryStage.setOnCloseRequest(event -> {
				System.exit(0);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
