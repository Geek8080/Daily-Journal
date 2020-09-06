package org.geek8080.journal.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geek8080.journal.entities.Entity;
import org.h2.tools.Server;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

public class Database {

    private static final Logger LOGGER = LogManager.getLogger(Database.class);

    private static Database instance;
    private static String DBName;
    private static Connection connection;
    private static Statement statement;
    private static Server server;
    private static HashSet<String> tables;
    private static HashMap<String, PreparedStatement> preparedStatements;

    private Database(String DBNameString){
        DBName = DBNameString;
        preparedStatements = new HashMap<>();

        try{
            LOGGER.info("Database Connection does not exist. Establishing Connection...");
            server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists").start();
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/./" + DBName + ";CREATE=true;CIPHER=AES","root","1024 1024");
            statement = connection.createStatement();
            LOGGER.info("Database connection established. " + server.getURL());
        } catch (SQLException ex) {
            LOGGER.fatal("Couldn't start H2 DB server.", ex);
        }
    }

    public static Database getInstance(String DBNameString, HashMap<String, String> tableQueryList){
        if(instance == null){
            instance = new Database(DBNameString);

            tables = getAllTables();
            tableQueryList.forEach((tableName, tableGenerationQuery) -> {
                if (!tables.contains(tableName)){
                    try {
                        LOGGER.info("Table " + tableName + " does not exist. Creating " + tableName + "...\n" + tableGenerationQuery);
                        statement.execute(tableGenerationQuery);
                        LOGGER.info("Successfully created " + tableName + ".");
                    } catch (SQLException ex) {
                        LOGGER.fatal("Encounterd an error while creating " + tableName + ". Query:\n" + tableGenerationQuery, ex);
                    }
                }
            });
        }
        return instance;
    }

    /**
     * Executes the given query on H2 database.
     *
     * @param query any SQL statement
     * @return true if the first result is a ResultSet object; false if it is an update count or there are no results
     */
    public boolean execute(String query){
        try{
            boolean b = statement.execute(query);
            LOGGER.info("Successfully executed query: " + query);
            return b;
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return false;
    }

    /**
     * Executes the given query on H2 database as preparedStatement.
     *
     * @param query any SQL statement
     * @return true if the first result is a ResultSet object; false if it is an update count or there are no results
     */
    public boolean execute(String query, HashMap<Integer, Object> valuePair){
        try{
            PreparedStatement preparedStatement;
            if (preparedStatements.containsKey(query)){
                preparedStatement = preparedStatements.get(query);
            }else{
                preparedStatement = connection.prepareStatement(query);
                preparedStatements.put(query, preparedStatement);
            }
            valuePair.forEach((k, v) -> {
                try{
                    preparedStatement.setObject(k, v);
                } catch (SQLException ex) {
                    LOGGER.error("Encountered an exception while setting parameters.", ex);
                }
            });
            boolean b = preparedStatement.execute();
            LOGGER.info("Successfully executed query: " + query);
            return b;
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return false;
    }

    /**
     * Executes the given SQL statement, which returns a single ResultSet object.
     * @param query query an SQL statement to be sent to the database, typically a static SQL SELECT statement
     * @return a ResultSet object that contains the data produced by the given query; never null
     */
    public ResultSet executeQuery(String query){
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            LOGGER.info("Successfully executed query: " + query);
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return rs;
    }

    /**
     * Executes the given SQL statement, which returns a single ResultSet object.
     * @param query query an SQL statement to be sent to the database, typically a static SQL SELECT statement
     * @return a ResultSet object that contains the data produced by the given query; never null
     */
    public ResultSet executeQuery(String query, HashMap<Integer, Object> valuePair){
        ResultSet rs = null;
        try {
            PreparedStatement preparedStatement;
            if(preparedStatements.containsKey(query)){
                preparedStatement = preparedStatements.get(query);
            }else {
                preparedStatement = connection.prepareStatement(query);
                preparedStatements.put(query, preparedStatement);
            }
            valuePair.forEach((k, v) -> {
                try {
                    preparedStatement.setObject(k, v);
                } catch (SQLException ex) {
                    LOGGER.error("Encountered an exception while setting parameters.", ex);
                }
            });
            rs = preparedStatement.executeQuery();
            LOGGER.info("Successfully executed query: " + query);
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return rs;
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.
     * @param query query n SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int executeUpdate(String query){
        int n = 0;
        try {
            n = statement.executeUpdate(query);
            LOGGER.info("Successfully executed query: " + query);
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return n;
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.
     * @param query query n SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int executeUpdate(String query, HashMap<Integer, Object> valuePair){
        int n = 0;
        try {
            PreparedStatement preparedStatement;
            if(preparedStatements.containsKey(query)){
                preparedStatement = preparedStatements.get(query);
            }else {
                preparedStatement = connection.prepareStatement(query);
                preparedStatements.put(query, preparedStatement);
            }
            valuePair.forEach((k, v) -> {
                try {
                    preparedStatement.setObject(k, v);
                } catch (SQLException ex) {
                    LOGGER.error("Encountered an exception while setting parameters.", ex);
                }
            });
            n = preparedStatement.executeUpdate();
            LOGGER.info("Successfully executed query: " + query);
        } catch (SQLException ex) {
            LOGGER.error("Encountered an error while executing: " + query, ex);
        }
        return n;
    }

    public void executePreparedStatement(String query, Entity entity){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            entity.createPreparedStatement(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static HashSet<String> getAllTables(){
        HashSet<String> tables = new HashSet<>();
        try{
            ResultSet resultSet = statement.executeQuery("SHOW TABLES FROM diary;");
            while (resultSet.next()){
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            LOGGER.fatal("Encountered and error while retrieving the tables.", ex);
        }
        return tables;
    }

    public void close(){
        try{
            LOGGER.info("Stopping DB Server...");
            connection.close();
            statement.close();
            server.stop();
            LOGGER.info("DB Server stopped Successfully.");
        } catch (SQLException ex) {
             LOGGER.error("Encountered an error while closing the server connection with the DB Server.", ex);
        }
    }
}
