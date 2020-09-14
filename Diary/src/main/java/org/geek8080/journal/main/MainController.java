package org.geek8080.journal.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.geek8080.journal.main.dialog.NewEntry;
import org.geek8080.journal.main.page.PageCardController;

import java.io.IOException;

public class MainController extends StackPane {

	public MainController(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		this.setManaged(true);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private StackPane rootStack;

	@FXML
	private JFXButton newButton;

	@FXML
	private JFXButton printButton;

	@FXML
	private JFXButton reportButton;

	@FXML
	private JFXButton refreshButton;

	@FXML
	private VBox pageBox;

	@FXML
	void generateReport(MouseEvent event) {

	}

	@FXML
	void newPage(MouseEvent event) {
		NewEntry dialogPane = new NewEntry();
		JFXDialog dialog = new JFXDialog(this, dialogPane, JFXDialog.DialogTransition.TOP);
		dialogPane.cancelButton.setOnMouseClicked(e -> {
			
		});
		dialog.show();
	}

	@FXML
	void printPDF(MouseEvent event) {

	}

	@FXML
	void refresh(MouseEvent event) {

	}

	public void initialize(){
		App.DIARY.getPages().forEach((id, page) -> {
			pageBox.getChildren().add(new PageCardController(page));
		});
	}

}
