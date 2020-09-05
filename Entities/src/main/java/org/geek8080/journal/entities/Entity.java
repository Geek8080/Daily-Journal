package org.geek8080.journal.entities;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.PreparedStatement;

/**
 * <p>Entity class represents a table and properties of object that can be stored in an H2 table.</p>
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public abstract class Entity {
    /**
     * The ID of each Entity is unique. This will be the primary key in the Table.
     */
    @Getter
    @EqualsAndHashCode.Include
    private int ID;

    /**
     * This query will help generate table if it does not exist.
     */
    @Getter
    private String SQLGenerationQuery;

    /**
     * This is a flag to check if we need to implement some trigger f0r the table for this Entity type.
     */
    @Getter
    private boolean implementsDBTrigger;

    public abstract void createPreparedStatement(PreparedStatement preparedStatement);

}
