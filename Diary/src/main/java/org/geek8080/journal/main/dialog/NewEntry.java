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
	private StackPane rootStack;

	@FXML
	private JFXTextField titleField;

	@FXML
	private JFXTextField subtitleField;

	@FXML
	private JFXTextArea bodyField;

	@FXML
	public JFXButton cancelButton;

	@FXML
	private JFXButton saveButton;

	@FXML
	private void save(MouseEvent event){
		String title = titleField.getText().trim();
		String subTitle = subtitleField.getText().trim();
		String body = bodyField.getText().trim();
		if( (title == null || title.equalsIgnoreCase("")) || (body == null || body.equalsIgnoreCase(""))){
			Pane p = new Pane();
			p.setPrefWidth(500);
			p.prefHeight(200);
			Text text = new Text("Titile and Body are compulsory, don't leave them blank. If they are not blank then report the bug.");
			text.setWrappingWidth(500);
			JFXDialog errorDialog = new JFXDialog(rootStack, p, JFXDialog.DialogTransition.CENTER);
			errorDialog.show();
			errorDialog.setOnDialogClosed(e -> {
				return;
			});
		}
		String query = "";
		HashMap<Integer, Object> values = new HashMap<>();
		values.put(1, title);
		if(subTitle == null || subTitle.equalsIgnoreCase("")){
			query = "INSERT INTO PAGE(TITLE, BODY) VALUES(?, ?)";
			values.put(2, body);
		}else{
			query = "INSERT INTO PAGE(TITLE, SUBTITLE, BODY) VALUES(?, ?, ?)";;
			values.put(2, subTitle);
			values.put(3, body);
		}
		try{
			App.DB.executeQuery(query, values);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Successfull Operation");
			alert.setContentText("Entered value into DB successfully");
			alert.showAndWait();
		}catch (RuntimeException ex){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Unsuccessfull Operation");
			alert.setContentText("Couldn't enter value into DB successfully, refer logs..." + ex.getMessage());
			alert.showAndWait();
		}
	}

}

