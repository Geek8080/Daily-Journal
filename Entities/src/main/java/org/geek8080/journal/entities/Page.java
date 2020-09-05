package org.geek8080.journal.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Page extends Entity{

    static {
        SQLGenerationQuery = "CREATE TABLE IF NOT EXIST new_table (" +
                "  ID` INT NOT NULL AUTO_INCREMENT," +
                "  Creation_Time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  Title VARCHAR(50) NOT NULL," +
                "  Subtitle VARCHAR(200)," +
                "  Body LONGTEXT NOT NULL," +
                "  PRIMARY KEY (ID));";
    }

    private Timestamp creationTime;

    private String title;

    private String subTitle;

    private StringBuffer body;

    private String creationTimeString;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd MMMM, yyyy[hh:mm aa]");

    public Page(int ID, Timestamp creationTime, String title, String subTitle, Clob body) {
        super(ID);
        this.creationTime = creationTime;
        this.title = title;
        this.subTitle = subTitle;
        this.body = new StringBuffer(clobToString(body));
        this.creationTimeString = SIMPLE_DATE_FORMAT.format(creationTime);
    }

    public static SimpleDateFormat getSIMPLE_DATE_FORMAT() {
        return Page.SIMPLE_DATE_FORMAT;
    }

    @Override
    public void createPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.title);
        preparedStatement.setString(2, this.subTitle);
        preparedStatement.setObject(3, this.body);
    }

    @Override
    public String getSQLGenerationQuery() {
        return SQLGenerationQuery;
    }

    public String clobToString(Clob data) {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = data.getCharacterStream();
            BufferedReader br = new BufferedReader(reader);

            String line;
            while(null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public Timestamp getCreationTime() {
        return this.creationTime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public StringBuffer getBody() {
        return this.body;
    }

    public String getCreationTimeString() {
        return this.creationTimeString;
    }

    public String toString(){

        String val = "[ID: " + getID() +", " +
                "Creation Time: " + getCreationTimeString() +", " +
                "Title: " + this.title + ", " +
                "Sub-Title: " + this.subTitle + ", " +
                "Body: " + this.body + "];";

        return val;
    }
}