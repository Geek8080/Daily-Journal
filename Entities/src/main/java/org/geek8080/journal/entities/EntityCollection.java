package org.geek8080.journal.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

@ToString
public abstract class EntityCollection<Integer, T extends Entity> extends HashMap {

    @Getter
    private HashMap<Integer, T> entries;

    public EntityCollection(){
        entries = new HashMap<>();
    }

    public T getByID(int id){
        return entries.get(id);
    }

}
