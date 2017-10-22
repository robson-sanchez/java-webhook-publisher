package com.rvvsanchez.libs.event.publisher.webhook.core;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import com.rvvsanchez.libs.event.publisher.webhook.http.DefaultHttpConnector;
import com.rvvsanchez.libs.event.publisher.webhook.http.HttpConnector;
import com.rvvsanchez.libs.event.publisher.webhook.http.HttpContentType;

/**
 * Class responsible for sending webhook events.
 * 
 * @author robson-sanchez
 */
public class WebhookPublisher {

//  private Object serializer;
//  
//  private Object securityContext;
//  
//  private Object eventIdentity;
//  
//  private Object auditContext;
//  
//  private Object retryPolicy;
//  
//  private Object invalidatePolicy;
  
  private ConnectionPool connectionPool;
  
  private HttpConnector connector;
  
  private WebhookPublisher() {}
  
  /**
   * Post JSON event.
   * 
   * @param body JSON body
   * @param destination Destination URL
   * @return HTTP response
   */
  public int sendJsonEvent(String body, String destination) throws IOException {
    return connector.postEvent(body, HttpContentType.APPLICATION_JSON, destination);
  }

  /**
   * Post XML event.
   * 
   * @param body XML body
   * @param destination Destination URL
   * @return HTTP response
   */
  public int sendXmlEvent(String body, String destination) throws IOException {
    return connector.postEvent(body, HttpContentType.APPLICATION_XML, destination);
  }

  /**
   * Post form url-encodend event.
   * 
   * @param body Form content
   * @param destination Destination URL
   * @return HTTP response
   */
  public int sendFormUrlEncoded(Map<String, String> body, String destination) throws IOException {
    String payload = body.entrySet().stream().map(map -> map.getKey() + "=" + map.getValue())
        .collect(Collectors.joining("&"));

    return connector.postEvent(payload, HttpContentType.FORM_URL_ENCODED, destination);
  }
  
//  private HttpResponse sendEvent(String address) {
//    HttpConnector connector = connectionPool.getConnector(address);
    
    // TODO Apply event identity
    // TODO Apply security context
    // TODO Audit HTTP request
    // TODO Send message
    // TODO Audit HTTP response
    // TODO Apply retry policy
    // TODO Apply invalidate policy
    
//    return new HttpResponse();
//  }
  
  public static class WebhookPublisherBuilder {
    
    private WebhookPublisher publisher;
    
    private WebhookPublisherBuilder() {
      this.publisher = new WebhookPublisher();
    }
    
    static WebhookPublisherBuilder getInstance() {
      return new WebhookPublisherBuilder();
    }
    
    public WebhookPublisherBuilder connector(HttpConnector connector) {
      if (connector == null) {
        throw new IllegalArgumentException("Invalid HTTP connector");
      }
      
      if (this.publisher.connectionPool != null) {
        throw new IllegalArgumentException("Connection Pool had already been defined");
      }
      
      this.publisher.connector = connector;
      return this;
    }
//    public WebhookPublisherBuilder serializer(Object serializer) {
//      if (serializer == null) {
//        throw new IllegalArgumentException("Invalid entity serializer");
//      }
//      
//      this.publisher.serializer = serializer;
//      return this;
//    }
//    
//    public WebhookPublisherBuilder security(Object security) {
//      if (security == null) {
//        throw new IllegalArgumentException("Invalid security context");
//      }
//      
//      this.publisher.securityContext = security;
//      return this;
//    }
//    
//    public WebhookPublisherBuilder eventIdentity(Object identity) {
//      if (identity == null) {
//        throw new IllegalArgumentException("Invalid event identity");
//      }
//      
//      this.publisher.eventIdentity = identity;
//      return this;
//    }
//    
//    public WebhookPublisherBuilder audit(Object audit) {
//      if (audit == null) {
//        throw new IllegalArgumentException("Invalid audit context");
//      }
//      
//      this.publisher.auditContext = audit;
//      return this;
//    }
//    
//    public WebhookPublisherBuilder retry(Object retry) {
//      if (retry == null) {
//        throw new IllegalArgumentException("Invalid retry policy");
//      }
//      
//      this.publisher.retryPolicy = retry;
//      return this;
//    }
//    
//    public WebhookPublisherBuilder invalidate(Object invalidate) {
//      if (invalidate == null) {
//        throw new IllegalArgumentException("Invalid invalidate policy");
//      }
//      
//      this.publisher.invalidatePolicy = invalidate;
//      return this;
//    }
    
    public WebhookPublisherBuilder connectionPool(ConnectionPool pool) {
      if (pool == null) {
        throw new IllegalArgumentException("Invalid connection pool");
      }
      
      if (this.publisher.connector != null) {
        throw new IllegalArgumentException("HTTP Connector had already been defined");
      }
      
      this.publisher.connectionPool = pool;
      return this;
    }
    
    public WebhookPublisher build() {
      if ((this.publisher.connectionPool == null) && (this.publisher.connector == null)) {
        this.publisher.connector = new DefaultHttpConnector();
      }

      return this.publisher;
    }
    
  }
  
}
