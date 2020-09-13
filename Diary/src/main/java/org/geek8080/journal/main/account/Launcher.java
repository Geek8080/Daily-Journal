package org.geek8080.journal.main.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.geek8080.journal.entities.Page;
import org.geek8080.journal.main.dialog.NewEntry;
import org.geek8080.journal.main.page.PageCardController;
import org.geek8080.journal.services.PDFReport;

import java.io.File;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;

public class Launcher extends Application {
	Stage stage;


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		StackPane pane = new StackPane();
		Page page = new Page(12, new Timestamp(909090), "Title", "Subtitle", "Body");
		PageCardController pageCard = new PageCardController(page);
		pane.getChildren().add(pageCard);
		Scene scene = new Scene(pane, 700, 700);
		this.stage.setScene(scene);
		this.stage.show();

		JFXDialog jfxDialog = new JFXDialog(pane, new NewEntry(), JFXDialog.DialogTransition.TOP);
		jfxDialog.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
