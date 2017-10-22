package com.rvvsanchez.libs.event.publisher.webhook.core;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpConnector;

/**
 * Abstract class to be extended by the connection pool provider.
 * 
 * @author robson-sanchez
 */
public abstract class ConnectionPool {

  protected int minSize = 1;
  
  protected int maxSize = 1;
  
  protected long idleTimeout = -1;
  
  protected HttpConnector connector;
  
  /**
   * Define pool min size.
   * 
   * @param size Min size
   * @return connection pool
   */
  public ConnectionPool setMinPoolSize(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Invalid size: " + size);
    }
    
    this.minSize = size;
    
    if (this.maxSize < size) {
      setMaxPoolSize(size);
    }

    return this;
  }
  
  /**
   * Define pool max size.
   * 
   * @param size Max size
   * @return connection pool
   */
  public ConnectionPool setMaxPoolSize(int size) {
    if (size < minSize) {
      throw new IllegalArgumentException("Invalid size: " + size);
    }
    
    this.maxSize = size;
    
    return this;
  }
  
  /**
   * Define idle timeout in miliseconds.
   * 
   * @param timeout Idle timeout
   * @return connection pool
   */
  public ConnectionPool setIdleTimeout(long timeout) {
    this.idleTimeout = timeout;
    return this;
  }
  
  /**
   * Get HTTP connector according to the address.
   * 
   * @return HTTP connector
   */
  protected abstract HttpConnector getConnector();
  
}
