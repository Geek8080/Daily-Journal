package org.geek8080.journal.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import org.geek8080.journal.entities.Diary;
import org.geek8080.journal.main.dialog.NewEntry;
import org.geek8080.journal.main.page.PageCardController;
import org.geek8080.journal.services.PDFReport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

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
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File location = directoryChooser.showDialog(null);

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

		JFXDialog dialog = new JFXDialog(rootStack, vBox, JFXDialog.DialogTransition.TOP);
		dialog.show();

		dialog.setOnDialogClosed((e) -> {
			return;
		});

		AtomicReference<String> fileName = new AtomicReference<>("file");

		button.setOnMouseClicked(e -> {
			fileName.set(fileNameField.getText());

			StackPane pane = new StackPane();
			VBox vBoxSpinner = new VBox();
			Text spinnerText = new Text("Printing...");
			spinnerText.setWrappingWidth(300);
			spinnerText.setTextAlignment(TextAlignment.CENTER);
			spinnerText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
			vBox.setPrefHeight(200);
			vBox.setPrefWidth(300);
			vBox.setSpacing(30);
			vBox.getChildren().add(new JFXSpinner());
			vBox.getChildren().add(spinnerText);
			pane.getChildren().add(vBox);
			pane.setPrefWidth(300);
			pane.setPrefHeight(250);
			JFXDialog jfxDialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.CENTER);
			rootStack.setMouseTransparent(true);
			jfxDialog.show();

			PDFReport.generatePDF(location.getAbsoluteFile().getAbsolutePath() + "\\", fileName.get(), App.DIARY);

			rootStack.setMouseTransparent(false);
			jfxDialog.close();
			dialog.close();

			pane.getChildren().remove(vBox);
			spinnerText.setText("PDF Report Generated Successfully");
			pane.getChildren().add(spinnerText);
			pane.setPrefWidth(400);
			jfxDialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.TOP);
			jfxDialog.show();

		});
	}

	@FXML
	void refresh(MouseEvent event) throws SQLException {
		Pane pane = new AnchorPane();
		pane.setPrefWidth(300);
		pane.setPrefHeight(200);
		pane.getChildren().add(new JFXSpinner());
		JFXDialog dialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.CENTER);
		dialog.show();
		rootStack.setMouseTransparent(true);
		App.DIARY = new Diary(App.DB.executeQuery("SELECT * FROM PAGE ORDER BY CREATION_TIME ASC;"));
		initialize();
		rootStack.setMouseTransparent(false);
		dialog.close();
	}

	public void initialize(){
		App.DIARY.getPages().forEach((id, page) -> {
			pageBox.getChildren().add(new PageCardController(page));
		});
	}

}
