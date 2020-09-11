package org.geek8080.journal.main.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.geek8080.journal.main.App;
import org.geek8080.journal.services.Authenticator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPController {

	Pattern pattern;
	Matcher matcher;

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
			try{
				App.AUTH.generateOTP(emailId);
				App.mailSent = true;
				otpTextField.setDisable(false);
				verifyButton.setDisable(false);
			}catch (RuntimeException ex){
				nointernetLabel.setText("*No Internet Connection. Exiting App...");
				nointernetLabel.setVisible(true);
				App.noInternet = true;
			}
		}else {
			nointernetLabel.setText("Invalid E-Mail ID");
			nointernetLabel.setVisible(true);
			emailTextField.setStyle("-fx-background-color: red");
		}
	}

	@FXML
	public void verify(MouseEvent event) {
		if(App.AUTH.authenticateOTP(otpTextField.getText())){
			App.verifiedOTP = true;
		}else{
			nointernetLabel.setText("Invalid OTP");
			nointernetLabel.setVisible(true);
			otpTextField.setStyle("-fx-background-color: red");
		}
	}

	public void initialize(){
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

}