package com.rvvsanchez.libs.event.publisher.webhook.core.event;

/**
 * 
 * 
 * @author robson-sanchez
 */
public abstract class EventIdentity {

  private String name;
  
  private String value;

  public EventIdentity(String name, String value) {
    this.name = name;
    this.value = value;
  }  
  
  
}
