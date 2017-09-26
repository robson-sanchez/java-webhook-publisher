package com.rvvsanchez.libs.event.publisher.webhook.http;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

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
@PrepareForTest({ HttpRequest.class, DefaultHttpConnector.class })
public class DefaultHttpConnectorTest {

  private static final String INVALID_DESTINATION = "mock://test^invalid";

  private static final String MOCK_USER_AGENT = "mockUserAgent";
  
  private static final String MOCK_DESTINATION = "http://test.libs.com";
  
  private static final String MOCK_JSON = "{ \"foo\": 123 }";
  
  private HttpRequestBuilder builder;
  
  private HttpRequest request;
  
  private HttpURLConnection connection;
  
  private DefaultHttpConnector connector;
  
  @Before
  public void init() {
    this.builder = mock(HttpRequestBuilder.class);
    this.request = mock(HttpRequest.class);
    this.connection = mock(HttpURLConnection.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() throws IOException {
    connector = new DefaultHttpConnector(MOCK_USER_AGENT);
    connector.postEvent(null, null, null);
  }
  
  @Test(expected = MalformedURLException.class)
  public void testInvalidDestination() throws IOException {
    connector = new DefaultHttpConnector(MOCK_USER_AGENT);
    connector.postEvent(null, null, INVALID_DESTINATION);
  }
  
  @Test
  public void testPostWithoutBody() throws IOException {
    connector = new DefaultHttpConnector(MOCK_USER_AGENT);
    
    mockHttpRequestBuilder();
    
    int responseCode = HttpURLConnection.HTTP_NOT_FOUND;
    doReturn(responseCode).when(connection).getResponseCode();
    
    assertEquals(responseCode, connector.postEvent(null, null, MOCK_DESTINATION));
  }
  
  @Test(expected = IOException.class)
  public void testPostWithBodyFailed() throws IOException {
    connector = new DefaultHttpConnector(MOCK_USER_AGENT, HttpConnector.DEFAULT_TIMEOUT, HttpConnector.DEFAULT_TIMEOUT);
    
    mockHttpRequestBuilder();
    
    DataOutputStream outputStream = mock(DataOutputStream.class);
    doThrow(IOException.class).when(outputStream).flush();
    
    doReturn(MOCK_JSON).when(request).getBody();
    doReturn(outputStream).when(connection).getOutputStream();
    
    connector.postEvent(MOCK_JSON, HttpContentType.APPLICATION_JSON, MOCK_DESTINATION);
  }
  
  @Test
  public void testPostWithBody() throws IOException {
    connector = new DefaultHttpConnector(MOCK_USER_AGENT, HttpConnector.DEFAULT_TIMEOUT, HttpConnector.DEFAULT_TIMEOUT);
    
    mockHttpRequestBuilder();
    
    doReturn(MOCK_JSON).when(request).getBody();
    doReturn(new ByteArrayOutputStream()).when(connection).getOutputStream();
    
    int responseCode = HttpURLConnection.HTTP_OK;
    doReturn(responseCode).when(connection).getResponseCode();
    
    assertEquals(responseCode, connector.postEvent(MOCK_JSON, HttpContentType.APPLICATION_JSON, MOCK_DESTINATION));
  }
  
  private void mockHttpRequestBuilder() throws IOException {
    PowerMockito.mockStatic(HttpRequest.class);
    
    when(HttpRequest.destination(MOCK_DESTINATION)).thenReturn(builder);
    
    doReturn(builder).when(builder).method(HttpMethod.POST);
    doReturn(builder).when(builder).header(any(HttpHeader.class));
    doReturn(builder).when(builder).body(anyString());
    
    doReturn(request).when(builder).build();
    doReturn(Collections.EMPTY_LIST).when(request).getHeaders();
    doReturn(HttpMethod.POST).when(request).getMethod();
    
    URI destination = mock(URI.class);
    URL destinationUrl = mock(URL.class);

    doReturn(destination).when(request).getDestination();
    when(destination.toURL()).thenReturn(destinationUrl);
    when(destinationUrl.openConnection()).thenReturn(connection);
  }
}
