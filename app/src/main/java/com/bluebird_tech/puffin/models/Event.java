package com.bluebird_tech.puffin.models;

import android.content.Context;
import android.provider.Settings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * @todo globally change date format
 */
@DatabaseTable(tableName = "events")
@JsonIgnoreProperties({"id"})
public class Event {
  private Context ctx;

  public static Event fromTension(Context ctx, Float tension) {
    Date now = new Date();
    Event event = new Event();

    event.setMeasurement("tension");
    event.setValue(tension);
    event.setCreatedAt(now);
    event.setUpdatedAt(now);
    event.setMeasuredAt(now);
    event.setCtx(ctx);

    return event;
  }

  @JsonProperty("deviceId")
  private String getDeviceId() {
    if (ctx == null)
      return "unknown";

    String android_id = Settings.Secure.getString(
      ctx.getApplicationContext().getContentResolver(),
      Settings.Secure.ANDROID_ID
    );

    return android_id;
  }

  @DatabaseField(generatedId = true)
  private Long id;

  @DatabaseField(canBeNull = false)
  private String measurement;

  @DatabaseField(canBeNull = false)
  private Float value;

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

  public void setCtx(Context ctx) {
    this.ctx = ctx;
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

  public Float getValue() { return value; }

  public void setValue(Float value) {
    this.value = value;
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
