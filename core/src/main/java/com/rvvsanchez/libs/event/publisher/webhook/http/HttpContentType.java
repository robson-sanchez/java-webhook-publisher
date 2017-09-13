package com.rvvsanchez.libs.event.publisher.webhook.http;

/**
 * Represents HTTP request content type.
 * 
 * @author robson-sanchez
 */
public enum HttpContentType {
  
  APPLICATION_JSON("application/json"),
  APPLICATION_XML("application/xml"),
  FORM_URL_ENCODED("application/x-www-form-urlencoded"),
  TEXT_PLAIN("text/plain");

  private String value;
  
  private HttpContentType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
}
