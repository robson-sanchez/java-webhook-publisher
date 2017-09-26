package com.rvvsanchez.libs.event.publisher.webhook.http;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpRequest.HttpRequestBuilder;

/**
 * Unit tests for {@link DefaultHttpConnector}
 * 
 * @author robson-sanchez
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpRequest.class })
public class BaseHttpConnectorTest {

  private static final String INVALID_DESTINATION = "mock://test^invalid";

  private static final String MOCK_USER_AGENT = "mockUserAgent";
  
  private static final String MOCK_DESTINATION = "http://test.libs.com";
  
  private static final String MOCK_JSON = "{ \"foo\": 123 }";
  
  private HttpRequestBuilder builder;
  
  private HttpRequest request;
  
  private HttpConnector connector = new MockHttpConnector(MOCK_USER_AGENT);
  
  @Before
  public void init() {
    this.builder = mock(HttpRequestBuilder.class);
    this.request = mock(HttpRequest.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() throws IOException {
    connector.postEvent(null, null, null);
  }
  
  @Test(expected = MalformedURLException.class)
  public void testInvalidDestination() throws IOException {
    connector.postEvent(null, null, INVALID_DESTINATION);
  }
  
  @Test
  public void testPostWithoutBody() throws IOException {
    assertEquals(HttpURLConnection.HTTP_NOT_FOUND, connector.postEvent(null, null, MOCK_DESTINATION));
  }
  
  @Test
  public void testPostWithBody() throws IOException {
    mockHttpRequestBuilder();
    
    doReturn(MOCK_JSON).when(request).getBody();
    
    assertEquals(HttpURLConnection.HTTP_OK, connector.postEvent(MOCK_JSON, HttpContentType.APPLICATION_JSON, MOCK_DESTINATION));
  }
  
  private void mockHttpRequestBuilder() throws IOException {
    PowerMockito.mockStatic(HttpRequest.class);
    
    when(HttpRequest.destination(MOCK_DESTINATION)).thenReturn(builder);
    
    doReturn(builder).when(builder).method(HttpMethod.POST);
    doReturn(builder).when(builder).header(any(HttpHeader.class));
    doReturn(builder).when(builder).body(anyString());
    
    doReturn(request).when(builder).build();
  }
}
