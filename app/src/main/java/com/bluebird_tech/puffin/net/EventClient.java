package com.bluebird_tech.puffin.net;

import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(
  rootUrl = "http://glacial-lake-1827.herokuapp.com",
//  rootUrl = "http://192.168.178.31:49100",
  converters = { MappingJackson2HttpMessageConverter.class },
  interceptors = { LoggingRequestInterceptor.class }
)
public interface EventClient extends RestClientErrorHandling {
  @Post("/events")
  void createEvent(Event event);
}
