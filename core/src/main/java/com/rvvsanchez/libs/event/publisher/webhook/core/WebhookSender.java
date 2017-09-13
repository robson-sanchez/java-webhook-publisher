package com.rvvsanchez.libs.event.publisher.webhook.core;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpConnector;
import com.rvvsanchez.libs.event.publisher.webhook.http.HttpContentType;

/**
 * Class responsible for sending webhook events.
 * 
 * @author robson-sanchez
 */
public class WebhookSender {

  /**
   * HTTP Connector
   */
  private HttpConnector connector;

  public WebhookSender(HttpConnector connector) {
    this.connector = connector;
  }

  /**
   * Post JSON event.
   * 
   * @param body JSON body
   * @param destination Destination URL
   * @return HTTP response code
   * @throws IOException Reports failure during message sent.
   */
  public int sendJsonEvent(String body, String destination) throws IOException {
    return connector.postEvent(body, HttpContentType.APPLICATION_JSON, destination);
  }

  /**
   * Post XML event.
   * 
   * @param body XML body
   * @param destination Destination URL
   * @return HTTP response code
   * @throws IOException Reports failure during message sent.
   */
  public int sendXmlEvent(String body, String destination) throws IOException {
    return connector.postEvent(body, HttpContentType.APPLICATION_XML, destination);
  }

  /**
   * Post form url-encodend event.
   * 
   * @param body Form content
   * @param destination Destination URL
   * @return HTTP response code
   * @throws IOException Reports failure during message sent.
   */
  public int sendFormUrlEncoded(Map<String, String> body, String destination) throws IOException {
    String payload = body.entrySet().stream().map(map -> map.getKey() + "=" + map.getValue())
        .collect(Collectors.joining("&"));

    return connector.postEvent(payload, HttpContentType.FORM_URL_ENCODED, destination);
  }

}
