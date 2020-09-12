package org.geek8080.journal.main.account;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.geek8080.journal.entities.User;
import org.geek8080.journal.main.App;

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
		JFXDialog jfxDialog = new JFXDialog(rootStack, new JFXSpinner(), JFXDialog.DialogTransition.CENTER);
		jfxDialog.setStyle("-fx-background-color: TRANSPARENT;");
		jfxDialog.show();
		new Thread(() -> {
			String userName = userNameTextField.getText().trim();
			String password = passwordField.getText().trim();
			App.USER = new User(userName, password);
			App.AUTH.saveUser(userName, password);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jfxDialog.close();
		});
	}

}
