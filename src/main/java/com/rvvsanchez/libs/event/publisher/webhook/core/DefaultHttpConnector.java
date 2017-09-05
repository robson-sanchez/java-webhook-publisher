package com.rvvsanchez.libs.event.publisher.webhook.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpConnector {

  private static final Integer DEFAULT_TIMEOUT = 5000;

  private String userAgent;

  private int connectTimeout;

  private int readTimeout;

  public DefaultHttpConnector(String userAgent) {
    this(userAgent, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  public DefaultHttpConnector(String userAgent, int connectTimeout, int readTimeout) {
    this.userAgent = userAgent;
    this.connectTimeout = connectTimeout;
    this.readTimeout = readTimeout;
  }

  public int postJsonEvent(String body, URL destination) throws IOException {
    return postEvent(body, "application/json", destination);
  }

  public int postApplicationXMLEvent(String body, URL destination) throws IOException {
    return postEvent(body, "application/xml", destination);
  }

  public int postFormUrlEncodedEvent(String body, URL destination) throws IOException {
    return postEvent(body, "application/x-www-form-urlencoded", destination);
  }

  public int postEvent(String body, String contentType, URL destination) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) destination.openConnection();

    // Request headers
    connection.setRequestMethod("POST");
    connection.setRequestProperty("User-Agent", userAgent);
    connection.setRequestProperty("Content-Type", contentType);
    connection.setRequestProperty("Accept", "*/*");

    // Timeouts
    connection.setConnectTimeout(connectTimeout);
    connection.setReadTimeout(readTimeout);

    // Send post request
    connection.setDoOutput(true);

    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
      wr.writeBytes(body);
      wr.flush();
    }

    return connection.getResponseCode();
  }

}
