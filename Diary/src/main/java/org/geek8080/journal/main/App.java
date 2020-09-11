package org.geek8080.journal.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.geek8080.journal.entities.Diary;
import org.geek8080.journal.entities.Page;
import org.geek8080.journal.entities.User;
import org.geek8080.journal.main.account.Launcher;
import org.geek8080.journal.services.Authenticator;
import org.geek8080.journal.services.Database;
import org.geek8080.journal.utils.FileHandler;

import java.util.HashMap;

public class App extends Application {

	public static User USER;
	public static Database DB;
	public static Authenticator AUTH;
	public static Diary DIARY;
	public static boolean loginAttempted = false;
	public static boolean loginSuccessful = false;
	public static boolean noInternet = false;
	public static boolean mailSent = false;
	public static boolean verifiedOTP = false;


	@Override
	public void start(Stage stage) throws Exception {

	}

	@Override
	public void init() throws Exception {

		String user = "USER";
		// Checking Files
		notifyPreloader(new Preloader.ProgressNotification(0.00d));
		AUTH = Authenticator.getInstance();
		if (FileHandler.exists("", user)){
			// Launching user login window
			while (!loginSuccessful) {
				notifyPreloader(new Preloader.ProgressNotification(0.05d));
				// Code to launch goes here
				while (!loginAttempted) ;
				if (loginSuccessful) {
					// Login Successful
					notifyPreloader(new Preloader.ProgressNotification(0.07d));
					break;
				}else {
					// Login Unsuccessful
					notifyPreloader(new Preloader.ProgressNotification(0.06d));
				}
			}
		}else {
			generateFile();
		}

		//Loading Database
		notifyPreloader(new Preloader.ProgressNotification(0.08));
		HashMap<String, String> tables = new HashMap<>();
		tables.put("Page", Page.getSQLGenerationQuery());
		DB = Database.getInstance("Diary", tables);

		//Database Loaded Successfully
		notifyPreloader(new Preloader.ProgressNotification(0.09));

		//Launching App
		notifyPreloader(new Preloader.ProgressNotification(0.10));
	}

	private void generateFile() {
		// Launching OTP verification Window
		notifyPreloader(new Preloader.ProgressNotification(0.01d));
		// Code to launch goes here
		Launcher.main(new String[]{});

		notifyPreloader(new Preloader.ProgressNotification(0.02d));
		while (!mailSent) {
			// Sending mail...
			if (noInternet) {
				notifyPreloader(new Preloader.ProgressNotification(0.11d));
			}
		}

		while (!verifiedOTP);

		// Launching Sign-Up Window
		notifyPreloader(new Preloader.ProgressNotification(0.03d));
		// Code to launch window goes here
		while (USER == null);

		// User registered successfully
		notifyPreloader(new Preloader.ProgressNotification(0.04d));
	}

	public static void main(String[] args) {
		System.setProperty("javafx.preloader", "org.geek8080.journal.main.preloader.PreloaderFX");
		Application.launch(App.class, args);
	}
}
