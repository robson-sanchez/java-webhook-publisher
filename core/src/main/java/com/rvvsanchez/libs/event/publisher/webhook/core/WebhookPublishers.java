package com.rvvsanchez.libs.event.publisher.webhook.core;

import com.rvvsanchez.libs.event.publisher.webhook.core.WebhookPublisher.WebhookPublisherBuilder;

/**
 * Class responsible for building webhook publishers.
 * 
 * @author robson-sanchez
 */
public class WebhookPublishers {

  private WebhookPublishers() {}

  /**
   * Builds the default webhook publiser.
   * 
   * @return Default webhook publisher
   */
  public static WebhookPublisher createDefault() {
    return WebhookPublisherBuilder.getInstance().build();
  }
  
  /**
   * Creates a new webhook publisher builder.
   * 
   * @return Webhook Publisher Builder
   */
  public static WebhookPublisherBuilder custom() {
    return WebhookPublisherBuilder.getInstance();
  }
  
}
