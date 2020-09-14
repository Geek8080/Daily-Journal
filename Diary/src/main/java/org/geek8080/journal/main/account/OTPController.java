package org.geek8080.journal.main.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.geek8080.journal.main.App;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPController {

	public static Stage pstage;
	Pattern pattern;
	Matcher matcher;

	public static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@FXML
	private StackPane rootStack;

	@FXML
	private AnchorPane rootPane;

	@FXML
	public JFXTextField emailTextField;

	@FXML
	public JFXTextField otpTextField;

	@FXML
	public Text nointernetLabel;

	@FXML
	public JFXButton verifyButton;

	@FXML
	public void send(MouseEvent event) {
		String emailId = emailTextField.getText().trim();
		matcher = pattern.matcher(emailId);
		if (matcher.matches()){
			nointernetLabel.setVisible(false);

			JFXSpinner spinner = new JFXSpinner();
			Text text = new Text("Sending mail");
			text.setFont(Font.font("Times New Roman", 24));
			VBox box = new VBox();
			box.getChildren().add(spinner);
			box.getChildren().add(text);
			box.setAlignment(Pos.CENTER);
			box.setPrefHeight(rootStack.getPrefHeight());
			box.setPrefWidth(rootStack.getPrefWidth());
			box.setSpacing(30);
			text.setWrappingWidth(300);
			text.setTextAlignment(TextAlignment.CENTER);

			JFXDialog jfxDialog = new JFXDialog(rootStack, box, JFXDialog.DialogTransition.CENTER);
			jfxDialog.setPrefHeight(rootStack.getPrefHeight());
			jfxDialog.setPrefWidth(rootStack.getPrefWidth());
			jfxDialog.setStyle("-fx-background-color: transparent");
			rootStack.setMouseTransparent(true);
			jfxDialog.show();
			//prashantprakash97@gmail.com
			new Thread( () -> {
				try{
					emailTextField.setDisable(true);
					App.AUTH.generateOTP(emailId);
					otpTextField.setDisable(false);
					verifyButton.setDisable(false);
					emailTextField.setDisable(false);
				}catch (RuntimeException ex){
					nointernetLabel.setText("*No Internet Connection. Exiting App...");
					nointernetLabel.setVisible(true);
				}finally {
					rootStack.setMouseTransparent(false);
					jfxDialog.close();
				}
			}).start();
		}else {
			nointernetLabel.setText("Invalid E-Mail ID");
			nointernetLabel.setVisible(true);
			emailTextField.setStyle("-fx-border-color: red");
		}
	}

	@FXML
	public void verify(MouseEvent event) throws IOException {
		if(App.AUTH.authenticateOTP(otpTextField.getText().trim())){
			launchSignUp();
		}else{
			nointernetLabel.setText("Invalid OTP");
			nointernetLabel.setVisible(true);
			otpTextField.setStyle("-fx-border-color: red");
		}
	}

	@FXML
	public void launchSignUp() throws IOException {
		Stage stage = new Stage();

		Parent root = FXMLLoader.load(App.class.getResource("account/Signup.fxml"));
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Signup");
		stage.initStyle(StageStyle.UNDECORATED);

		SignupController.pstage = stage;
		stage.show();
		pstage.hide();

		stage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}

	public void initialize(){
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

}