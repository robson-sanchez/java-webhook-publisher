package com.rvvsanchez.libs.event.publisher.webhook.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rvvsanchez.libs.event.publisher.webhook.http.DefaultHttpConnector;
import com.rvvsanchez.libs.event.publisher.webhook.http.HttpContentType;

/**
 * Unit tests for {@link WebhookSender}
 * 
 * @author robson-sanchez
 */
@RunWith(MockitoJUnitRunner.class)
public class WebhookSenderTest {

  private static final String MOCK_JSON = "{ \"foo\": 123 }";

  private static final String MOCK_XML = "<foo>123</foo>";

  private static final String MOCK_FORM = "foo=123&bar=456";

  private static final String MOCK_DESTINATION = "http://test.libs.com";

  @Mock
  private DefaultHttpConnector connector;

  private WebhookSender sender;

  @Before
  public void init() {
    this.sender = new WebhookSender(connector);
  }

  @Test(expected = IOException.class)
  public void testSendJsonFailure() throws IOException {
    doThrow(IOException.class).when(connector).postEvent(MOCK_JSON, HttpContentType.APPLICATION_JSON, MOCK_DESTINATION);
    sender.sendJsonEvent(MOCK_JSON, MOCK_DESTINATION);
  }

  @Test
  public void testSendJsonSuccess() throws IOException {
    doReturn(HttpURLConnection.HTTP_OK).when(connector).postEvent(MOCK_JSON, HttpContentType.APPLICATION_JSON,
        MOCK_DESTINATION);
    assertEquals(HttpURLConnection.HTTP_OK, sender.sendJsonEvent(MOCK_JSON, MOCK_DESTINATION));
  }

  @Test(expected = IOException.class)
  public void testSendXmlFailure() throws IOException {
    doThrow(IOException.class).when(connector).postEvent(MOCK_XML, HttpContentType.APPLICATION_XML, MOCK_DESTINATION);
    sender.sendXmlEvent(MOCK_XML, MOCK_DESTINATION);
  }

  @Test
  public void testSendXmlSuccess() throws IOException {
    doReturn(HttpURLConnection.HTTP_OK).when(connector).postEvent(MOCK_XML, HttpContentType.APPLICATION_XML,
        MOCK_DESTINATION);
    assertEquals(HttpURLConnection.HTTP_OK, sender.sendXmlEvent(MOCK_XML, MOCK_DESTINATION));
  }

  @Test(expected = IOException.class)
  public void testSendFormFailure() throws IOException {
    doThrow(IOException.class).when(connector).postEvent(MOCK_FORM, HttpContentType.FORM_URL_ENCODED, MOCK_DESTINATION);

    Map<String, String> payload = new LinkedHashMap<>();
    payload.put("foo", "123");
    payload.put("bar", "456");

    sender.sendFormUrlEncoded(payload, MOCK_DESTINATION);
  }

  @Test
  public void testSendFormSuccess() throws IOException {
    doReturn(HttpURLConnection.HTTP_OK).when(connector).postEvent(MOCK_FORM, HttpContentType.FORM_URL_ENCODED,
        MOCK_DESTINATION);

    Map<String, String> payload = new LinkedHashMap<>();
    payload.put("foo", "123");
    payload.put("bar", "456");

    assertEquals(HttpURLConnection.HTTP_OK, sender.sendFormUrlEncoded(payload, MOCK_DESTINATION));
  }

}
