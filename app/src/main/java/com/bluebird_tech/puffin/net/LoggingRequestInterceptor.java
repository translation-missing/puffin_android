package com.bluebird_tech.puffin.net;

import android.util.Log;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
  private static final String TAG = LoggingRequestInterceptor.class.getSimpleName();

  @Override
  public ClientHttpResponse intercept(
    HttpRequest request,
    byte[] body,
    ClientHttpRequestExecution execution
  ) throws IOException {
    ClientHttpResponse response = execution.execute(request, body);

    Log.d(TAG, "HEADERS = " + request.getHeaders());
    Log.d(TAG, "URI     = " + request.getURI());
    Log.d(TAG, "METHOD  = " + request.getMethod());
    Log.d(TAG, "BODY    = " + new String(body));

    return response;
  }
}
