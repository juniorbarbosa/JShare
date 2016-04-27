package br.univel.client;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class MainClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainClient.fxml"));
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cliente");
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
