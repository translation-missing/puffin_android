package com.bluebird_tech.puffin.net;

import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(
  rootUrl = "http://glacial-lake-1827.herokuapp.com",
  converters = { MappingJackson2HttpMessageConverter.class }
  // interceptors = { DebugLoggingInterceptor.class }
)
public interface EventClient {
  @Post("/events")
  void createEvent(Event event);
}
