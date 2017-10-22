package com.rvvsanchez.libs.event.publisher.webhook.core.event;


public class Event<T> {

  private EventIdentity identity;
  
  private T content;

  public Event(EventIdentity identity, T content) {
    this.identity = identity;
    this.content = content;
  }
  
}
