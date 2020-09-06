package org.geek8080.journal.services;

import org.geek8080.journal.entities.Page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseTest {
	public static void main(String[] args) {
		String DBName = "Diary";
		HashMap<String, String> queries = new HashMap<>();
		queries.put("Page", Page.getSQLGenerationQuery());
		System.out.println(queries);
		Database database = Database.getInstance(DBName, queries);

		String insertionQuery = "INSERT INTO PAGE(Title, SubTitle, Body) VALUES(?, ?, ?);";
		String selectionQuery = "SELECT * FROM PAGE;";
		String dropQuery = "DROP TABLE PAGE;";

		HashMap<Integer, Object> valuePair = new HashMap<>();

		String diaryTitle = "First Entry";
		String diarySubtitle = "First Entry";
		String diaryBody = "This is the first entry";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		diaryTitle = "Second Entry";
		diarySubtitle = "Second Entry";
		diaryBody = "This is the second entry";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		diaryTitle = "Third Entry";
		diarySubtitle = "Third Entry";
		diaryBody = "This is the Third entry";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		ResultSet rs = database.executeQuery(selectionQuery);

		int i = 0;

		try {
			while (rs.next()){
				System.out.println(i);
				Page page = new Page(rs.getInt("ID"), rs.getTimestamp("Creation_Time"), rs.getString("Title")
										, rs.getString("Subtitle"), rs.getClob("Body"));
				System.out.println(page.toString());
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		// database.execute(dropQuery);
		database.close();
	}
}
