package com.bluebird_tech.puffin.models;

import java.util.Date;

import android.content.Context;
import android.provider.Settings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "events")
@JsonIgnoreProperties({"id", Event.FIELD_UPLOADED_AT})
public class Event {
  private Context ctx;

  public static final String FIELD_MEASUREMENT = "measurement";
  public static final String FIELD_MEASURED_AT = "measured_at";
  public static final String FIELD_UPLOADED_AT = "uploaded_at";

  public static Event fromTensionAndNote(
        Context ctx, Float tension, String note) {
    Date now = new Date();
    Event event = new Event();

    event.setMeasurement("tension");
    event.setValue(tension);
    event.setNote(note);
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

  @DatabaseField(
    canBeNull = false,
    index = true,
    columnName = FIELD_MEASUREMENT
  )
  private String measurement;

  @DatabaseField(canBeNull = false)
  private Float value;

  @DatabaseField
  private String tags;

  @DatabaseField
  private String note;

  @DatabaseField(canBeNull = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  @DatabaseField(canBeNull = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  @DatabaseField(index = true, columnName = Event.FIELD_MEASURED_AT)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date measuredAt;

  @DatabaseField(canBeNull = true, index = true, columnName = FIELD_UPLOADED_AT)
  private Date uploadedAt;

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

  public Float getValue() {
    return value;
  }

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

  public Date getUploadedAt() {
    return uploadedAt;
  }

  public void setUploadedAt(Date uploadedAt) {
    this.uploadedAt = uploadedAt;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
