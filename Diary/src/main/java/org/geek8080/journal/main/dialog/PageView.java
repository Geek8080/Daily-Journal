package org.geek8080.journal.main.dialog;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.geek8080.journal.entities.Page;

import java.io.IOException;

public class PageView extends StackPane{

	public Page page;

	public PageView(Page page){
		this.page = page;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("PageView.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private StackPane rootStack;

	@FXML
	private Text subtitleText;

	@FXML
	private Text titleText;

	@FXML
	public JFXButton okayButton;

	@FXML
	private Text timeText;

	@FXML
	private Text bodyText;

	public void initialize(){
		titleText.setText(page.getTitle());
		String subTitle = page.getSubTitle();
		subtitleText.setText((subTitle == null || subTitle.equals(null) || subTitle=="null")?"":subTitle);
		bodyText.setText(page.getBody().toString());
		timeText.setText(page.getCreationTimeString());
	}

}

