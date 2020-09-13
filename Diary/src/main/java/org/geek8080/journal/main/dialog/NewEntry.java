package org.geek8080.journal.main.dialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewEntry extends AnchorPane {

	public NewEntry(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewEntry.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		this.setManaged(true);

		try{
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private JFXTextField titleField;

	@FXML
	private JFXTextField subtitleField;

	@FXML
	private JFXTextArea bodyField;

	@FXML
	private JFXButton cancelButton;

	@FXML
	private JFXButton saveButton;

}

