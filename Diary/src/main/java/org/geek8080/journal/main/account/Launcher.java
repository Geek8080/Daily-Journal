package org.geek8080.journal.main.account;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
	Stage stage;


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("OTP.fxml"));
		Scene scene = new Scene(root);
		this.stage.setScene(scene);
		this.stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
