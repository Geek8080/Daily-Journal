package org.geek8080.journal.entities.test;

import org.geek8080.journal.entities.Diary;
import org.geek8080.journal.entities.Page;

import javax.sql.rowset.serial.SerialClob;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EntityTest {
    public static void main(String[] args) throws SQLException {
        Timestamp time1 = new Timestamp(627290);
        Timestamp time2 = new Timestamp(6272900);
        Timestamp time3 = new Timestamp(62729000);
        Timestamp time4 = new Timestamp(627290000);

        Page page1 = new Page(1, time1, "First entry", "", new SerialClob(new char[]{'f', 'i', 'r', 's', 't'}));
        Page page2 = new Page(2, time2, "Second entry", "", new SerialClob(new char[]{'f', 'i', 'r', 's', 't'}));
        Page page3 = new Page(3, time3, "Third entry", "", new SerialClob(new char[]{'f', 'i', 'r', 's', 't'}));
        Page page4 = new Page(4, time4, "Fourth entry", "", new SerialClob(new char[]{'f', 'i', 'r', 's', 't'}));

        Diary diary = new Diary();
        diary.getPages().put(1, page1);
        diary.getPages().put(2, page2);
        diary.getPages().put(3, page3);
        diary.getPages().put(4, page4);

        System.out.println(diary.toString());
    }
}
