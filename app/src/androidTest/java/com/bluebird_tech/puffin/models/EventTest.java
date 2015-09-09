package com.bluebird_tech.puffin.models;
import junit.framework.TestCase;

public class EventTest extends TestCase {
  public void testGetDeviceId() throws Exception {
    Event event = new Event();
    assertEquals("unknown", event.getDeviceId());
    assertEquals("hallo", "welt");
  }
}
