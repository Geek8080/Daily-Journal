module Diary {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.geek8080.journal.main to javafx.fxml;
    exports org.geek8080.journal.main;
}