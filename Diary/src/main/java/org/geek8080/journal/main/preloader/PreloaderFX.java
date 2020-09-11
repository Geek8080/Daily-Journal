package org.geek8080.journal.main.preloader;

import javafx.application.Preloader;
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
		if (preloaderNotification instanceof ProgressNotification){
			int progress = (int)Math.floor(((ProgressNotification)preloaderNotification).getProgress()*100);
			String text = "Loading";

			switch (progress){
				case 0: text = "Checking Files";
					break;
				case 1: text = "Launching OTP verification Window";
					break;
				case 2: text = "Sending mail...";
					break;
				case 3: text = "Launching Sign-Up Window";
					break;
				case 4: text = "User registered successfully";
					break;
				case 5: text = "Launching user login window";
					break;
				case 6: text = "Login Unsuccessful";
					break;
				case 7: text = "Login Successful";
					break;
				case 8: text = "Loading Database";
					break;
				case 9: text = "Database Loaded Successfully";
					break;
				case 10: text = "Launching App";
					break;
				default: SplashController.loading.setText("No Internet Connection. Exiting App...");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.exit(-1);
			}
			System.out.println(text);
			SplashController.loading.setText(text);
		}
	}
}
