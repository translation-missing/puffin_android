package com.bluebird_tech.puffin.net;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

@EBean
public class ErrorHandler implements RestErrorHandler {
  @Override
  public void onRestClientExceptionThrown(NestedRuntimeException e) {
    e.printStackTrace();
  }
}
