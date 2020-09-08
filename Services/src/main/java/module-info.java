module Services {
    requires java.scripting;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.pdfbox;
    requires org.apache.fontbox;
    requires java.sql;
    requires com.h2database;
    requires Entities;
    requires java.desktop;
    exports org.geek8080.journal.services;
}