package org.geek8080.journal.main.dialog;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.geek8080.journal.main.App;

import java.io.IOException;
import java.util.HashMap;

public class NewEntry extends StackPane {

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
	public StackPane rootStack;

	@FXML
	public JFXTextField titleField;

	@FXML
	public JFXTextField subtitleField;

	@FXML
	public JFXTextArea bodyField;

	@FXML
	public JFXButton cancelButton;

	@FXML
	public JFXButton saveButton;

}

