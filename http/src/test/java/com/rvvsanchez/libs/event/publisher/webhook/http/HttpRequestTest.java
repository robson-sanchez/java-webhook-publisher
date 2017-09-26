package com.rvvsanchez.libs.event.publisher.webhook.http;

import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.ACCEPT;
import static com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequestHeaders.CONTENT_LENGTH;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for {@link HttpRequest}
 * 
 * @author robson-sanchez
 */
public class HttpRequestTest {

  private static final String INVALID_DESTINATION = "mock://test^invalid";
  
  private static final String MOCK_DESTINATION = "http://test.libs.com";
  
  private static final String MOCK_JSON = "{ \"foo\": 123 }";
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullMethod() {
    HttpRequest.method(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() throws MalformedURLException {
    HttpRequest.destination(null);
  }
  
  @Test(expected = MalformedURLException.class)
  public void testInvalidDestination() throws MalformedURLException {
    HttpRequest.destination(INVALID_DESTINATION);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullBody() {
    HttpRequest.method(HttpMethod.GET).body(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullHeader() {
    HttpRequest.method(HttpMethod.POST).header(null);
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInvalidRequestNullDestination() {
    HttpRequest.method(HttpMethod.PUT).build();
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInvalidRequestNullMethod() throws MalformedURLException {
    HttpRequest.destination(MOCK_DESTINATION).build();
  }
  
  @Test
  public void testValidRequest() throws MalformedURLException {
    HttpHeader acceptHeader = new HttpHeader(ACCEPT, null);
    
    Integer contentLength = new Integer(MOCK_JSON.length());
    HttpHeader contentLengthHeader = new HttpHeader(CONTENT_LENGTH, contentLength);

    HttpRequest request = HttpRequest.destination(MOCK_DESTINATION).method(HttpMethod.HEAD).header(acceptHeader)
        .header(contentLengthHeader).body(MOCK_JSON).build();
    
    assertEquals(MOCK_DESTINATION, request.getDestination().toURL().toExternalForm());
    assertEquals(HttpMethod.HEAD, request.getMethod());
    assertEquals(MOCK_JSON, request.getBody());
    
    List<HttpHeader> headers = request.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(ACCEPT, headers.get(0).getKey());
    assertEquals("", headers.get(0).getValue());
    assertEquals(CONTENT_LENGTH, headers.get(1).getKey());
    assertEquals(contentLength.toString(), headers.get(1).getValue());
  }
}
