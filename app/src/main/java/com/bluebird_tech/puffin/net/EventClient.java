package com.bluebird_tech.puffin.net;

import com.bluebird_tech.puffin.BuildConfig;
import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(
  rootUrl = BuildConfig.API_URL,
  converters = { MappingJackson2HttpMessageConverter.class },
  interceptors = { LoggingRequestInterceptor.class }
)
public interface EventClient extends RestClientErrorHandling {
  @Post("/events")
  void createEvent(Event event);
}
