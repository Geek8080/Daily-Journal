package org.geek8080.journal.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.geek8080.journal.main.page.PageCardController;

public class MainController {

	public static StackPane parent;

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

		int dialogWidth = 650;
		int dialogHeight = 800;

		VBox containerBox = new VBox();
		containerBox.setSpacing(20);
		containerBox.setPrefWidth(600);
		containerBox.setPrefHeight(750);
		containerBox.setAlignment(Pos.TOP_LEFT);

		Text windowTitle = new Text("NEW DIARY ENTRY");
		windowTitle.setFont(Font.font("Times New Roman", 28));
		windowTitle.setWrappingWidth(600);
		windowTitle.setTextAlignment(TextAlignment.CENTER);
		containerBox.getChildren().add(windowTitle);

		HBox titleBox = new HBox(400);
		titleBox.setSpacing(10);
		titleBox.setAlignment(Pos.BOTTOM_CENTER);
		Text titleText = new Text("Title: ");
		titleText.setFont(Font.font("Verdana",14));
		JFXTextField titleField = new JFXTextField();
		titleField.setPrefWidth(350);
		titleBox.getChildren().add(titleText);
		titleBox.getChildren().add(titleField);
		containerBox.getChildren().add(titleBox);

		HBox subtitlebox = new HBox(500);
		subtitlebox.setSpacing(10);
		subtitlebox.setAlignment(Pos.BOTTOM_CENTER);
		Text subtitleText = new Text("Subtitle: ");
		subtitleText.setFont(Font.font("Verdana", 14));
		JFXTextField subtitleField = new JFXTextField();
		subtitleField.setPrefWidth(400);
		subtitlebox.getChildren().add(subtitleText);
		subtitlebox.getChildren().add(subtitleField);
		containerBox.getChildren().add(subtitlebox);

		JFXTextField bodyField = new JFXTextField();
		bodyField.setPromptText("Body");
		bodyField.setLabelFloat(true);
		bodyField.setPrefWidth(600);
		bodyField.setPrefHeight(250);
		bodyField.setMaxHeight(250);
		containerBox.getChildren().add(bodyField);

		JFXButton okButton = new JFXButton("OK");
		okButton.setButtonType(JFXButton.ButtonType.RAISED);
		okButton.setCursor(Cursor.HAND);
		okButton.setStyle("-fx-border-radius: 5em");
		okButton.setStyle("-fx-background-radius: 5em");
		okButton.setStyle("-fx-background-color: forestgreen");
		okButton.setStyle("-fx-text-fill: cornsilk");
		okButton.setFont(Font.font("Arial", 16));

		JFXButton closeButton = new JFXButton("Cancel");
		closeButton.setButtonType(JFXButton.ButtonType.RAISED);
		closeButton.setCursor(Cursor.HAND);
		closeButton.setStyle("-fx-border-radius: 5em");
		closeButton.setStyle("-fx-background-radius: 5em");
		closeButton.setStyle("-fx-background-color: red");
		closeButton.setStyle("-fx-text-fill: cornsilk");
		closeButton.setFont(Font.font("Arial", 16));

		HBox buttonBox = new HBox();
		buttonBox.setSpacing(50);
		buttonBox.setPrefWidth(600);
		buttonBox.setAlignment(Pos.CENTER_LEFT);
		buttonBox.getChildren().add(closeButton);
		buttonBox.getChildren().add(okButton);

		containerBox.getChildren().add(buttonBox);

		JFXDialog jfxDialog = new JFXDialog(parent, containerBox, JFXDialog.DialogTransition.TOP);
		jfxDialog.show();
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
