package org.geek8080.journal.main.page;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.geek8080.journal.entities.Page;
import org.geek8080.journal.main.App;
import org.geek8080.journal.services.PDFReport;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class PageCardController extends StackPane{

	public static StackPane parent;
	public Page page;

	@FXML
	private StackPane cardStack;

	@FXML
	private AnchorPane card;

	@FXML
	private Text titleText;

	@FXML
	private Text subtitleText;

	@FXML
	private Text dateText;

	@FXML
	private Text contentText;

	@FXML
	private JFXButton viewButton;

	@FXML
	private JFXButton printButton;

	@FXML
	private JFXButton deleteButton;

	public PageCardController(Page page){
		this.page = page;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PageCard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		this.setManaged(true);

		try{
			loader.load();
			titleText.setText(page.getTitle());
			subtitleText.setText(page.getSubTitle());
			dateText.setText(page.getCreationTimeString());
			contentText.setText(page.getBody().substring(0, Math.min(100, page.getBody().length())) + "...");
			JFXDepthManager.setDepth(this, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void delete(MouseEvent event) {
		App.DB.executeQuery("DELETE FROM PAGE WHERE ID=" + page.getID() + ";");
		// reload main
	}

	@FXML
	void print(MouseEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File directory = directoryChooser.showDialog(null);

		Text fileNameText = new Text("Enter File name: ");
		fileNameText.setFont(Font.font(16));
		JFXTextField fileNameField = new JFXTextField();
		fileNameField.setFont(Font.font(15));
		fileNameField.setPrefWidth(250);
		Text txt = new Text("PDF File Report");
		txt.setFont(Font.font(24));
		JFXButton button = new JFXButton("OK");


		HBox box = new HBox();
		box.getChildren().add(fileNameText);
		box.getChildren().add(fileNameField);
		box.setSpacing(20);
		box.setAlignment(Pos.BOTTOM_CENTER);

		VBox vBox = new VBox();
		vBox.getChildren().add(txt);
		vBox.getChildren().add(box);
		vBox.getChildren().add(button);
		vBox.setSpacing(20);
		vBox.setPrefHeight(250);
		vBox.setPrefWidth(450);
		vBox.setAlignment(Pos.CENTER);

		JFXDialog dialog = new JFXDialog(parent, vBox, JFXDialog.DialogTransition.TOP);
		parent.setDisable(true);
		dialog.show();

		dialog.setOnDialogClosed((e) -> {
			parent.setDisable(false);
		});

		AtomicReference<String> fileName = new AtomicReference<>("file");

		button.setOnMouseClicked(e -> {
			fileName.set(fileNameField.getText());
			dialog.close();
		});

		PDFReport.generatePDF(directory.getAbsoluteFile().getAbsolutePath() + "\\", fileName.get(), page);

	}

	@FXML
	void view(MouseEvent event) {

	}

}