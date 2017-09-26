package com.rvvsanchez.libs.event.publisher.webhook.http.apache;

import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_LENGTH;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.rvvsanchez.libs.event.publisher.webhook.http.BaseHttpConnector;
import com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequest;

/**
 * Http connector using Apache HTTP client.
 * 
 * @author robson-sanchez
 */
public class ApacheHttpConnector extends BaseHttpConnector {

  private RequestConfig requestConfig;

  public ApacheHttpConnector(String userAgent) {
    this(userAgent, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  public ApacheHttpConnector(String userAgent, int connectTimeout, int readTimeout) {
    super(userAgent);
    this.requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build();
  }
  
  /**
   * Post webhook event using Apache HTTP client.
   * 
   * @param request HTTP request
   * @throws IOException Reports failure during message sent.
   */
  @Override
  protected int execute(HttpRequest request) throws IOException {
    HttpPost post = new HttpPost(request.getDestination());

    // Request headers
    List<String> unsupported = unsupportedHeaders();
    request.getHeaders().stream().filter((header) -> !unsupported.contains(header.getKey()))
        .forEach((header) -> post.addHeader(header.getKey(), header.getValue()));

    try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        CloseableHttpResponse response = httpclient.execute(post);) {
      return response.getStatusLine().getStatusCode();
    }
  }

  /**
   * Retrieves the list of unsupported headers for this connector.
   * 
   * @return list of unsupported headers
   */
  private List<String> unsupportedHeaders() {
    return Arrays.asList(CONTENT_LENGTH);
  }
}
