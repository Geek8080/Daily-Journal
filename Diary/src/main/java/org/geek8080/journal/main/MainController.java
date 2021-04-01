package org.geek8080.journal.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
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
import org.geek8080.journal.services.ExcelReport;
import org.geek8080.journal.services.PDFReport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File location = directoryChooser.showDialog(null);

		Text fileNameText = new Text("Enter File name: ");
		fileNameText.setFont(Font.font(16));
		JFXTextField fileNameField = new JFXTextField();
		fileNameField.setFont(Font.font(15));
		fileNameField.setPrefWidth(250);
		Text txt = new Text("Excel Report");
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
			Text spinnerText = new Text("Generating...");
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

			new Thread(() -> {
				ExcelReport.generateExcelReport(location.getAbsoluteFile().getAbsolutePath() + "\\", fileName.get(), App.DIARY);
			});

			rootStack.setMouseTransparent(false);
			jfxDialog.close();
			dialog.close();

			pane.getChildren().remove(vBox);
			spinnerText.setText("Excel Report Generated Successfully");
			pane.getChildren().add(spinnerText);
			pane.setPrefWidth(400);
			jfxDialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.TOP);
			jfxDialog.show();

		});
	}

	@FXML
	void newPage(MouseEvent event) {
		NewEntry dialogPane = new NewEntry();
		JFXDialog dialog = new JFXDialog(this, dialogPane, JFXDialog.DialogTransition.TOP);
		dialogPane.cancelButton.setOnMouseClicked(e -> {
			dialog.close();
			try {
				refreshVBox();
			} catch (SQLException throwables) {
				StackPane pane = new StackPane();
				Text text = new Text("Error Refreshing the pages.. Click Refresh to view changes");
				text.setWrappingWidth(350);
				pane.getChildren().add(text);
				pane.setPrefWidth(400);
				pane.setPrefHeight(300);
				JFXDialog jfxDialog = new JFXDialog(this, pane, JFXDialog.DialogTransition.CENTER);
				jfxDialog.show();
				throwables.printStackTrace();
			}
		});
		dialogPane.saveButton.setOnMouseClicked(e -> {
			String title = dialogPane.titleField.getText().trim();
			String subTitle = dialogPane.subtitleField.getText().trim();
			String body = dialogPane.bodyField.getText().trim();
			if( (title == null || title.equalsIgnoreCase("")) || (body == null || body.equalsIgnoreCase(""))){
				Pane p = new Pane();
				p.setPrefWidth(500);
				p.prefHeight(200);
				Text text = new Text("Title and Body are compulsory, don't leave them blank. If they are not blank then report the bug.");
				text.setWrappingWidth(500);
				JFXDialog errorDialog = new JFXDialog(dialogPane.rootStack, p, JFXDialog.DialogTransition.CENTER);
				errorDialog.show();
				errorDialog.setOnDialogClosed(e1 -> {
					return;
				});
			}
			String query = "";
			HashMap<Integer, Object> values = new HashMap<>();
			values.put(1, title);
			if(subTitle == null || subTitle.equalsIgnoreCase("")){
				query = "INSERT INTO PAGE(TITLE, BODY) VALUES(?, ?);";
				values.put(2, body);
			}else{
				query = "INSERT INTO PAGE(TITLE, SUBTITLE, BODY) VALUES(?, ?, ?);";
				values.put(2, subTitle);
				values.put(3, body);
			}
			try{
				App.DB.executeUpdate(query, values);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setWidth(350);
				alert.setTitle("Successful Operation");
				alert.setContentText("Entered value into DB successfully");
				alert.show();
				alert.setOnCloseRequest(event1 -> {
					dialog.close();
					try {
						refreshVBox();
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
				});
			}catch (RuntimeException ex){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setWidth(350);
				alert.setTitle("Unsuccessful Operation");
				alert.setContentText("Couldn't enter value into DB successfully, refer logs..." + ex.getMessage());
				alert.show();
				alert.setOnCloseRequest(event1 -> {
					dialog.close();
				});
			}
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
		refreshVBox();
	}

	public void refreshVBox() throws SQLException {
		Pane pane = new AnchorPane();
		pane.setPrefWidth(300);
		pane.setPrefHeight(200);
		pane.getChildren().add(new JFXSpinner());
		JFXDialog dialog = new JFXDialog(rootStack, pane, JFXDialog.DialogTransition.CENTER);
		dialog.show();
		rootStack.setMouseTransparent(true);
		App.DIARY = new Diary(App.DB.executeQuery("SELECT * FROM PAGE ORDER BY CREATION_TIME DESC;"));
		pageBox.getChildren().clear();
		populate();
		rootStack.setMouseTransparent(false);
		dialog.close();
	}

	public void populate(){
		App.DIARY.getPages().forEach((id, page) -> {
			pageBox.getChildren().add(new PageCardController(page));
		});
	}

	public void initialize(){
		PageCardController.parent = this;
		populate();
	}

}
