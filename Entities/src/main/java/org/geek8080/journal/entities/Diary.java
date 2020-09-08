package org.geek8080.journal.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Diary {

    private HashMap<Integer, Page> pages;

    private static Timestamp firstEntry;
    private static Timestamp lastEntry;

    public Diary(){
        pages = new HashMap<>();
    }

    public Diary(Map<Integer, Page> map){
        pages = new HashMap<>();
        map.forEach((pageID, page) -> {
            firstEntry = minDate(firstEntry, page.getCreationTime());
            lastEntry = maxDate(lastEntry, page.getCreationTime());
            pages.put(pageID, page);
        });
    }

    public Diary(ResultSet rs) throws SQLException {
        pages = new HashMap<>();
        while (rs.next()){
            Page page = new Page(rs.getInt("ID"), rs.getTimestamp("Creation_Time"), rs.getString("Title")
                    , rs.getString("Subtitle"), rs.getString("Body"));
            firstEntry = minDate(firstEntry, page.getCreationTime());
            lastEntry = maxDate(lastEntry, page.getCreationTime());
            pages.put(page.getID(), page);
        }
    }

    private Timestamp minDate(Timestamp a, Timestamp b) {
        return (a == null) ? b : ((b==null) ? a : (a.before(b) ? a : b));
    }

    private Timestamp maxDate(Timestamp a, Timestamp b) {
        return (a == null) ? b : ((b==null) ? a : (a.after(b) ? a : b));
    }

    public static Timestamp getFirstEntry() {
        return firstEntry;
    }

    public static Timestamp getLastEntry() {
        return lastEntry;
    }

    public HashMap<Integer, Page> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return pages.toString();
    }
}
