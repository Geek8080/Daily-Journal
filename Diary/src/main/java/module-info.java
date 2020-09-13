module Diary {
    requires javafx.controls;
    requires javafx.fxml;
    requires Entities;
    requires Utils;
    requires Services;
    requires com.jfoenix;
	requires java.sql;

	opens org.geek8080.journal.main to javafx.fxml;
    opens org.geek8080.journal.main.account to javafx.fxml;
    opens org.geek8080.journal.main.preloader to javafx.fxml;
    opens org.geek8080.journal.main.page to javafx.fxml;
    opens org.geek8080.journal.main.dialog to javafx.fxml;

    exports org.geek8080.journal.main;
    exports org.geek8080.journal.main.preloader;
    exports org.geek8080.journal.main.account;
    exports org.geek8080.journal.main.page;
}