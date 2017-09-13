package com.rvvsanchez.libs.event.publisher.webhook.http;

/**
 * Holds the HTTP header content.
 * 
 * @author robson-sanchez
 */
public class HttpHeader {

  private String key;
  
  private Object value;
  
  public HttpHeader(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value != null ? value.toString() : "";
  }
  
}
