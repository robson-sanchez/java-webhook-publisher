package com.rvvsanchez.libs.event.publisher.webhook.http;

import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.ACCEPT;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_LENGTH;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_TYPE;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.USER_AGENT;

import java.io.IOException;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequest.HttpRequestBuilder;

/**
 * Abstract class for low-level HTTP connectors
 * 
 * @author robson-sanchez
 */
public abstract class BaseHttpConnector implements HttpConnector {
  
  protected String userAgent;

  protected int connectTimeout;

  protected int readTimeout;

  public BaseHttpConnector(String userAgent) {
    this(userAgent, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  public BaseHttpConnector(String userAgent, int connectTimeout, int readTimeout) {
    this.userAgent = userAgent;
    this.connectTimeout = connectTimeout;
    this.readTimeout = readTimeout;
  }
  
  @Override
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

  protected abstract int execute(HttpRequest request) throws IOException;
  
}
