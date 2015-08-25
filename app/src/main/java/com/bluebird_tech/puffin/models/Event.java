package com.bluebird_tech.puffin.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

/**
 * @todo globally change date format
 */
@DatabaseTable(tableName = "events")
public class Event {
  public static Event fromTension(String tension) {
    Date now = new Date();
    Event event = new Event();

    event.setMeasurement("tension");
    event.setFields(tension);
    event.setCreatedAt(now);
    event.setUpdatedAt(now);
    event.setMeasuredAt(now);

    return event;
  }

  @DatabaseField(generatedId = true)
  private Long id;

  @DatabaseField(canBeNull = false)
  private String measurement;

  @DatabaseField(canBeNull = false)
  private String fields;

  @DatabaseField
  private String tags;

  @DatabaseField(canBeNull = false)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  @DatabaseField(canBeNull = false)
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

//    @DatabaseField
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    private Date deletedAt;

  @DatabaseField
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date measuredAt;

  /* required */
  public Event() {
  }

    /* generated getters/setters */

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMeasurement() {
    return measurement;
  }

  public void setMeasurement(String measurement) {
    this.measurement = measurement;
  }

  public String getFields() {
    return fields;
  }

  public void setFields(String fields) {
    this.fields = fields;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getMeasuredAt() {
    return measuredAt;
  }

  public void setMeasuredAt(Date measuredAt) {
    this.measuredAt = measuredAt;
  }

//    public Date getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(Date deletedAt) {
//        this.deletedAt = deletedAt;
//    }
}
