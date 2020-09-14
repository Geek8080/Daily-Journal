package org.geek8080.journal.main.account;

import com.jfoenix.controls.*;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.geek8080.journal.entities.User;
import org.geek8080.journal.main.App;
import org.geek8080.journal.main.Main;

public class SignupController {

	public static Stage pstage;

	@FXML
	private JFXTextField userNameTextField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private StackPane rootStack;

	@FXML
	void cancel(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void signup(MouseEvent event) throws InterruptedException {
		StackPane pane = new StackPane();
		pane.setPrefHeight(200);
		pane.setPrefWidth(300);
		pane.getChildren().add(new JFXSpinner());
		pane.setAlignment(Pos.CENTER);
		pane.setCenterShape(true);
		JFXDialog jfxDialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.CENTER);
		rootStack.setDisable(true);
		jfxDialog.show();

		String userName = userNameTextField.getText().trim();
		String password = passwordField.getText().trim();
		App.USER = new User(userName, password);
		App.AUTH.saveUser(userName, password);
		rootStack.setDisable(false);

		try {
			Main.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		jfxDialog.close();
		pstage.hide();
	}

}
