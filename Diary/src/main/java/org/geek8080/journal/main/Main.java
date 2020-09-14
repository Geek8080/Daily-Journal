package org.geek8080.journal.main;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.effects.JFXDepthManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main {
	public static void start(Stage stage) throws Exception {
		MainController mainWindow = new MainController();
		mainWindow.setMaxHeight(mainWindow.getPrefHeight());
		mainWindow.setMaxWidth(mainWindow.getPrefWidth());
		mainWindow.setStyle("-fx-background-color: antiquewhite");

		StackPane mainContainer = new StackPane();
		mainContainer.setAlignment(mainWindow, Pos.CENTER);
		mainContainer.getChildren().add(mainWindow);

		JFXDecorator decoratorWindow = new JFXDecorator(stage, mainContainer);
		decoratorWindow.setAlignment(Pos.CENTER);
		decoratorWindow.setCenterShape(true);
		decoratorWindow.setCustomMaximize(true);

		decoratorWindow.setBackground(new Background(new BackgroundImage(
				new Image(Main.class.getResource("background.jpg").toExternalForm(), mainContainer.getMaxWidth(), mainContainer.getMaxHeight(),true,true),
				BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT)));

		JFXDepthManager.setDepth(mainContainer, 1500);

		stage.setScene(new Scene(decoratorWindow));
		stage.show();
	}
}
