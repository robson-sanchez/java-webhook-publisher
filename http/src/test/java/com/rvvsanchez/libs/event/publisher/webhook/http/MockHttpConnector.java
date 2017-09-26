package com.rvvsanchez.libs.event.publisher.webhook.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Mock http connector.
 * 
 * @author robson-sanchez
 */
public class MockHttpConnector extends BaseHttpConnector {

  public MockHttpConnector(String userAgent) {
    super(userAgent);
  }

  @Override
  protected int execute(HttpRequest request) throws IOException {
    if ("".equals(request.getBody())) {
      return HttpURLConnection.HTTP_NOT_FOUND;
    }
    
    return HttpURLConnection.HTTP_OK;
  }

}
