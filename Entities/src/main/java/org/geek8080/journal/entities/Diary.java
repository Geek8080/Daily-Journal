package org.geek8080.journal.entities;

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

    public Diary(Map map){
        pages = new HashMap<>();
        pages.putAll(map);
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
