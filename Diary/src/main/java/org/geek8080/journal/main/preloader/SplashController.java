package org.geek8080.journal.main.preloader;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SplashController {

	@FXML
	public Text titleText;

	@FXML
	public Text loadingText;

	public static Text loading;

	public void initialize(){
		loading = this.loadingText;
	}
}