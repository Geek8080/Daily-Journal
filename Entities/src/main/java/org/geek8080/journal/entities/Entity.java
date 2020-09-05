package org.geek8080.journal.entities;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>Entity class represents a table and properties of object that can be stored in an H2 table.</p>
 */
public abstract class Entity {
    /**
     * The ID of each Entity is unique. This will be the primary key in the Table.
     */
    private int ID;

    public static String SQLGenerationQuery;

    public Entity(int ID){
        this.ID = ID;
    }

    public abstract void createPreparedStatement(PreparedStatement preparedStatement) throws SQLException;

    public abstract String getSQLGenerationQuery();

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Entity)) return false;
        final Entity other = (Entity) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.ID != other.ID) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Entity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.ID;
        return result;
    }

    public int getID() {
        return this.ID;
    }
}
