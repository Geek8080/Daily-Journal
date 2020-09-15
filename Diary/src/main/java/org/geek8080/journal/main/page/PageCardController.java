package org.geek8080.journal.main.page;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;

import org.geek8080.journal.entities.Page;
import org.geek8080.journal.main.App;
import org.geek8080.journal.main.Main;
import org.geek8080.journal.services.ExcelReport;
import org.geek8080.journal.services.PDFReport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class PageCardController extends AnchorPane{

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
	void delete(MouseEvent event) throws SQLException {
		App.DB.executeUpdate("DELETE FROM PAGE WHERE ID=" + page.getID() + ";");
		Main.mainWindow.refreshVBox();
		// reload main
	}

	@FXML
	void print(MouseEvent event) {
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

		JFXDialog dialog = new JFXDialog(parent, vBox, JFXDialog.DialogTransition.TOP);
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
			JFXDialog jfxDialog = new JFXDialog(parent, pane, JFXDialog.DialogTransition.CENTER);
			parent.setMouseTransparent(true);
			jfxDialog.show();

			PDFReport.generatePDF(location.getAbsoluteFile().getAbsolutePath() + "\\", fileName.get(), App.DIARY);

			parent.setMouseTransparent(false);
			jfxDialog.close();
			dialog.close();

			pane.getChildren().remove(vBox);
			spinnerText.setText("PDF Report Generated Successfully");
			pane.getChildren().add(spinnerText);
			pane.setPrefWidth(400);
			jfxDialog = new JFXDialog(parent, pane, JFXDialog.DialogTransition.TOP);
			jfxDialog.show();

		});
	}

	@FXML
	void view(MouseEvent event) {

	}

}