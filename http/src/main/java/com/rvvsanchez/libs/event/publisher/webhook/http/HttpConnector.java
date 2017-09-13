package com.rvvsanchez.libs.event.publisher.webhook.http;

import java.io.IOException;

/**
 * Interface to be implemented for low-level connectors.
 * 
 * @author robson-sanchez
 */
public interface HttpConnector {

  /**
   * Post webhook event.
   * 
   * @param body Webhook payload
   * @param contentType Webhook content type
   * @param destination Destination URL
   * @throws IOException Reports failure during message sent.
   */
  int postEvent(String body, HttpContentType contentType, String destination) throws IOException;
  
}
