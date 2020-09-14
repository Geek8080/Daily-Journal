package org.geek8080.journal.main.account;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.geek8080.journal.entities.Page;
import org.geek8080.journal.main.App;
import org.geek8080.journal.main.Main;

import java.io.IOException;

public class LogInController {

	public static int attempts = 5;

	public static Stage pstage;

	@FXML
	private StackPane rootStack;

	@FXML
	private JFXTextField userNameTextField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	void cancel(MouseEvent event) {
		JFXButton yesButton = new JFXButton("YES");
		yesButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#6da426"), CornerRadii.EMPTY, Insets.EMPTY)));
		yesButton.setButtonType(JFXButton.ButtonType.RAISED);
		yesButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		yesButton.setCursor(Cursor.HAND);
		yesButton.setTextFill(Color.valueOf("#e5d7d7"));

		JFXButton noButton = new JFXButton("NO");
		noButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#ef2929"), CornerRadii.EMPTY, Insets.EMPTY)));
		noButton.setButtonType(JFXButton.ButtonType.RAISED);
		noButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		noButton.setCursor(Cursor.HAND);
		noButton.setTextFill(Color.valueOf("#d6edb9"));

		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(noButton);
		buttonBox.getChildren().add(yesButton);
		buttonBox.setSpacing(30);
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setActions(buttonBox);
		dialogLayout.setHeading(new Text("Are you Sure?"));
		JFXDialog jfxDialog = new JFXDialog(rootStack, dialogLayout, JFXDialog.DialogTransition.CENTER);
		jfxDialog.show();
		noButton.setOnMouseClicked(e -> {
			jfxDialog.close();
		});
		yesButton.setOnMouseClicked(e->{
			System.exit(0);
		});
	}

	@FXML
	void login(MouseEvent event) throws Exception {
		String userName = userNameTextField.getText().trim();
		String password = passwordField.getText().trim();
		if(App.AUTH.authenticateUser(userName, password)){
			Main.start(new Stage());
			pstage.hide();
		}else{

			if(attempts < 0){
				launchOTPWindow();
				pstage.hide();
				return;
			}

			JFXButton okButton = new JFXButton("OK");
			okButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#6da426"), CornerRadii.EMPTY, Insets.EMPTY)));
			okButton.setButtonType(JFXButton.ButtonType.RAISED);
			okButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
			okButton.setCursor(Cursor.HAND);
			okButton.setTextFill(Color.valueOf("#e5d7d7"));

			JFXButton forgetButton = new JFXButton("Forget Password?");
			forgetButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#ef2929"), CornerRadii.EMPTY, Insets.EMPTY)));
			forgetButton.setButtonType(JFXButton.ButtonType.RAISED);
			forgetButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
			forgetButton.setCursor(Cursor.HAND);
			forgetButton.setTextFill(Color.valueOf("#d6edb9"));

			HBox buttonBox = new HBox();
			buttonBox.getChildren().add(forgetButton);
			buttonBox.getChildren().add(okButton);
			buttonBox.setSpacing(30);
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setActions(buttonBox);
			dialogLayout.setHeading(new Text("Login failed"));
			dialogLayout.setBody(new Text("Incorrect Username/Password. " + attempts-- + " attempts left."));
			JFXDialog jfxDialog = new JFXDialog(rootStack, dialogLayout, JFXDialog.DialogTransition.CENTER);
			jfxDialog.show();

			forgetButton.setOnMouseClicked(e -> {
				launchOTPWindow();
				pstage.hide();
			});
			okButton.setOnMouseClicked(e->{
				jfxDialog.close();
			});
		}
	}

	@FXML
	private void launchOTPWindow() {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("account/OTP.fxml"));
			stage.setTitle("OTP Verification");
			OTPController.pstage = stage;
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			stage.setOnCloseRequest(e -> {
				System.exit(0);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
