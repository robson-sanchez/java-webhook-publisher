package com.rvvsanchez.libs.event.publisher.webhook.http;

import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.ACCEPT;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_LENGTH;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_TYPE;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.USER_AGENT;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequest.HttpRequestBuilder;

/**
 * Low-level HTTP connector
 * 
 * @author robson-sanchez
 */
public class DefaultHttpConnector implements HttpConnector {

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
   * Post webhook event using Java Standard library.
   * 
   * @param body Webhook payload
   * @param contentType Webhook content type
   * @param destination Destination URL
   * @throws IOException Reports failure during message sent.
   */
  public int postEvent(String body, HttpContentType contentType, String destination) throws IOException {
    HttpHeader acceptHeader = new HttpHeader(ACCEPT, "*/*");
    
    String typeValue = contentType == null ? HttpContentType.TEXT_PLAIN.getValue() : contentType.getValue();
    HttpHeader contentTypeHeader = new HttpHeader(CONTENT_TYPE, typeValue);
    
    int contentLength = body == null ? 0 : body.length();
    HttpHeader contentLengthHeader = new HttpHeader(CONTENT_LENGTH, contentLength);
    
    HttpHeader userAgentHeader = new HttpHeader(USER_AGENT, userAgent);

    HttpRequestBuilder builder = HttpRequest.destination(destination).method(HttpMethod.POST).header(acceptHeader)
        .header(contentTypeHeader).header(contentLengthHeader).header(userAgentHeader);

    if (body != null) {
      builder = builder.body(body);
    }
    
    HttpRequest request = builder.build();
    
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
