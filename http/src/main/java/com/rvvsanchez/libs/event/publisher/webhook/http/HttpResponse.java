package com.rvvsanchez.libs.event.publisher.webhook.http;

import java.util.List;

/**
 * Holds HTTP response headers, payload, and status code.
 * 
 * @author robson-sanchez
 */
public class HttpResponse {

  private List<HttpHeader> headers;
  
  private Object payload;
  
  private HttpStatus status;
  
}
