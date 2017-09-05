package com.rvvsanchez.libs.event.publisher.webhook.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebhookSender {

  private static final String USER_AGENT = "Webhook-Sender";

  private DefaultHttpConnector connector;

  public static void main(String[] args) throws MalformedURLException, IOException {
    DefaultHttpConnector connector = new DefaultHttpConnector(USER_AGENT);

    WebhookSender sender = new WebhookSender(connector);

    sender.sendJsonEvent("{ \"test\": 1234 }", new URL(args[0]));

    sender.sendXmlEvent("<test>1234</test>", new URL(args[0]));

    Map<String, String> content = new HashMap<>();
    content.put("test", "1234");

    sender.sendFormUrlEncoded(content, new URL(args[0]));
  }

  public WebhookSender(DefaultHttpConnector connector) {
    this.connector = connector;
  }

  public void sendJsonEvent(String body, URL destination) throws IOException {
    connector.postJsonEvent(body, destination);
  }

  public void sendXmlEvent(String body, URL destination) throws IOException {
    connector.postApplicationXMLEvent(body, destination);
  }

  public void sendFormUrlEncoded(Map<String, String> body, URL destination) throws IOException {
    String payload = body.entrySet().stream().map(map -> map.getKey() + "=" + map.getValue())
        .collect(Collectors.joining("&"));

    connector.postFormUrlEncodedEvent(payload, destination);
  }

}
