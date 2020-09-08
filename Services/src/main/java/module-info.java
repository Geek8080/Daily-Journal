module Services {
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.pdfbox;
    requires org.apache.fontbox;
    requires com.h2database;
    requires poi.ooxml;
    requires poi;
    requires java.sql;
    requires java.scripting;
    requires java.desktop;
    requires Entities;
    requires Utils;
    requires java.mail;
    requires activation;

    exports org.geek8080.journal.services;
}