package org.geek8080.journal.services;

import org.geek8080.journal.entities.Diary;
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

		String diaryTitle = "The Day I didn't care about anything.";
		String diarySubtitle = "This day will be etched in memory forever.";
		String diaryBody = "Dear Diary,\n" +
				"\n" +
				"After I woke up this morning, I made the regrettable decision of waking up. " +
				"I tried to go back to sleep, but I could not even relax. " +
				"Today was one of those days where either I get up, or I get up. " +
				"I then decided to go outside, but because it was raining, I got wet. " +
				"I went back inside, felt tired enough to go back to bed, " +
				"but I still had to change out of my wet clothes. Then " +
				"I got a phone call from you know who. I got so excited that " +
				"I didn’t care that I was naked, tired, and there was a puddle on the floor. " +
				"We are going on a date this Friday. I don’t know how I’m going to sleep. " +
				"What am I going to wear? :naked dance: :singing in the rain: oh diary, " +
				"one day I’ll look back on this and say remember when I didn’t pay attention " +
				"to anything but the phone ringing. FINALLY.\n";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		diaryTitle = "D-Day- The Placement";
		diarySubtitle = "Hard work finally paid the dues.";
		diaryBody = "Dear Diary,\n\n" +
				"With all the covid situation, I was very disheartened and pretty depressed." +
				" I didn't know whether I will be placed or not. PayTM was the first " +
				"company that was allowing us. I wanted this badly. The whole process was a roler-coaster " +
				"drive. At last, it was years of hard work paying off and I got placed.";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		diaryTitle = "My damn baby cries a lot.";
		diarySubtitle = "Mr. Criesalot is the one keeping me away from you. It's not me its the piece of flesh.";
		diaryBody = "Dear Diary,\n" +
				"\n" +
				"Its been so long since I last shared anything with you, " +
				"to be precise 7 months. " +
				"Last time when we met was the day before I entered into labour room. " +
				"Motherhood is what has kept me away from you for so long, " +
				"I have so many things to share. Wait, " +
				"I think baby has got up. I need to go, will share all the " +
				"things with you some other day hopefully soon. :)\n";

		valuePair.put(1, diaryTitle);
		valuePair.put(2, diarySubtitle);
		valuePair.put(3, diaryBody);
		database.executeUpdate(insertionQuery, valuePair);

		ResultSet rs = database.executeQuery(selectionQuery);

		int i = 0;

		try {
			Diary diary = new Diary(rs);
			PDFReport.generatePDF("", "File", diary);
			System.out.println(diary.toString());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		// database.execute(dropQuery);
		database.close();
	}
}
