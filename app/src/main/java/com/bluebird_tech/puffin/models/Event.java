package com.bluebird_tech.puffin.models;

import java.util.Date;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "events")
public class Event {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String measurement;

    @DatabaseField
    private String fields;

    @DatabaseField
    private String tags;

    @DatabaseField
    private Date createdAt;

    @DatabaseField
    private Date updatedAt;

    @DatabaseField
    private Date deletedAt;
}
