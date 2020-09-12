package org.geek8080.journal.main.preloader;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PreloaderFX extends Preloader {

	Scene scene;
	Stage stage;


	@Override
	public void init() throws Exception {
		System.out.println(getClass().getResource("Splash.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
		scene = new Scene(root);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		this.stage.setScene(scene);
		this.stage.initStyle(StageStyle.UNDECORATED);
		this.stage.setTitle("Journal-Diary");
		this.stage.show();
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		StateChangeNotification.Type type = stateChangeNotification.getType();
		switch (type){
			case BEFORE_START:
				this.stage.hide();
				break;

		}
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification preloaderNotification) {
	}
}
