package com.rvvsanchez.libs.event.publisher.webhook.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds HTTP request headers, payload, method, and destination.
 * 
 * @author robson-sanchez
 */
public class HttpRequest {
  
  private HttpMethod method;
  
  private List<HttpHeader> headers;

  private String body;
  
  private URL destination;
  
  private HttpRequest() {
    this.headers = new ArrayList<>();
    this.body = "";
  }
  
  public HttpMethod getMethod() {
    return method;
  }

  public List<HttpHeader> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public URL getDestination() {
    return destination;
  }

  /**
   * Creates a {@link HttpRequestBuilder} object according the HTTP method passed as parameter.
   * 
   * @param method HTTP method
   * @return HTTP Request builder
   */
  public static HttpRequestBuilder method(HttpMethod method) {
    return new HttpRequestBuilder().method(method);
  }
  
  /**
   * Creates a {@link HttpRequestBuilder} object according the destination URL passed as parameter.
   * 
   * @param destination destination URL
   * @return HTTP Request builder
   * @throws MalformedURLException Reports an invalid URL
   */
  public static HttpRequestBuilder destination(String destination) throws MalformedURLException {
    return new HttpRequestBuilder().destination(destination);
  }
  
  /**
   * Builder class to create {@link HttpRequest} objects.
   * 
   * @author robson-sanchez
   */
  public static class HttpRequestBuilder {
    
    private HttpRequest request;
    
    private HttpRequestBuilder() {
      this.request = new HttpRequest();
    }
    
    public HttpRequestBuilder method(HttpMethod method) {
      if (method == null) {
        throw new IllegalArgumentException("Invalid HTTP method");
      }
      
      this.request.method = method;
      return this;
    }
    
    public HttpRequestBuilder header(HttpHeader header) {
      if (header == null) {
        throw new IllegalArgumentException("Invalid HTTP request header");
      }
      
      this.request.headers.add(header);
      return this;
    }
    
    public HttpRequestBuilder body(String body) {
      if (body == null) {
        throw new IllegalArgumentException("Invalid HTTP request body");
      }
      
      this.request.body = body;
      return this;
    }
    
    public HttpRequestBuilder destination(String destination) throws MalformedURLException {
      if (destination == null) {
        throw new IllegalArgumentException("Invalid destination URL");
      }
      
      this.request.destination = new URL(destination);
      return this;
    }
    
    public HttpRequest build() {
      if (this.request.method == null) {
        throw new IllegalStateException("Invalid HTTP request. HTTP method not provided");
      }
      
      if (this.request.destination == null) {
        throw new IllegalStateException("Invalid HTTP request. Destination URL not provided");
      }
      
      return request;
    }
  }
}
