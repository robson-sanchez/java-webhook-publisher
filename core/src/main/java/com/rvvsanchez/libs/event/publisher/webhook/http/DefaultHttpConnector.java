package com.rvvsanchez.libs.event.publisher.webhook.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Low-level HTTP connector
 * 
 * @author robson-sanchez
 */
public class DefaultHttpConnector extends BaseHttpConnector {

  public DefaultHttpConnector(String userAgent) {
    super(userAgent, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  public DefaultHttpConnector(String userAgent, int connectTimeout, int readTimeout) {
    super(userAgent, connectTimeout, readTimeout);
  }

  /**
   * Post webhook event using Java Standard Library.
   * 
   * @param request HTTP request
   * @return HTTP response code
   * @throws IOException Reports failure during message sent.
   */
  protected int execute(HttpRequest request) throws IOException {
    URL destination = request.getDestination().toURL();
    HttpURLConnection connection = (HttpURLConnection) destination.openConnection();

    connection.setRequestMethod(request.getMethod().name());
    
    // Request headers
    request.getHeaders().stream()
        .forEach((header) -> connection.setRequestProperty(header.getKey(), header.getValue()));
    
    // Timeouts
    connection.setConnectTimeout(connectTimeout);
    connection.setReadTimeout(readTimeout);

    connection.setDoOutput(true);

    if (request.getBody() != null) {
      try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
        wr.writeBytes(request.getBody());
        wr.flush();
      }
    }
    
    return connection.getResponseCode();
  }

}
