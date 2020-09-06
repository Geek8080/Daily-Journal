module Services {
    requires java.scripting;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires java.sql;
    requires com.h2database;
    requires Entities;
    exports org.geek8080.journal.services;
}