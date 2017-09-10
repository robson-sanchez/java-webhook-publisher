package com.rvvsanchez.libs.event.publisher.webhook.http;

import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.ACCEPT;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_LENGTH;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_TYPE;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.USER_AGENT;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Low-level HTTP connector
 * 
 * @author robson-sanchez
 */
public class DefaultHttpConnector {

  /**
   * Default HTTP connection timeout (in miliseconds)
   */
  private static final Integer DEFAULT_TIMEOUT = 5000;

  private String userAgent;

  private int connectTimeout;

  private int readTimeout;

  public DefaultHttpConnector(String userAgent) {
    this(userAgent, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  public DefaultHttpConnector(String userAgent, int connectTimeout, int readTimeout) {
    this.userAgent = userAgent;
    this.connectTimeout = connectTimeout;
    this.readTimeout = readTimeout;
  }

  /**
   * Post webhook event.
   * 
   * @param body Webhook payload
   * @param contentType Webhook content type
   * @param destination Destination URL
   * @throws IOException Reports failure during message sent.
   */
  public int postEvent(String body, HttpContentType contentType, String destination) throws IOException {
    HttpHeader acceptHeader = new HttpHeader(ACCEPT, "*/*");
    HttpHeader contentTypeHeader = new HttpHeader(CONTENT_TYPE, contentType.getValue());
    HttpHeader contentLengthHeader = new HttpHeader(CONTENT_LENGTH, body.length());
    HttpHeader userAgentHeader = new HttpHeader(USER_AGENT, userAgent);
    
    HttpRequest request = HttpRequest.destination(destination).method(HttpMethod.POST).header(acceptHeader)
        .header(contentTypeHeader).header(contentLengthHeader).header(userAgentHeader).body(body).build();
    
    return execute(request);
  }
  
  /**
   * Send HTTP request.
   * 
   * @param request HTTP request
   * @return HTTP response code
   * @throws IOException Reports failure during message sent.
   */
  private int execute(HttpRequest request) throws IOException {
    URL destination = request.getDestination();
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
