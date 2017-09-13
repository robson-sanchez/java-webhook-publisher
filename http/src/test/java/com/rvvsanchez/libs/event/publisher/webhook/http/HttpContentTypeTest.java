package com.rvvsanchez.libs.event.publisher.webhook.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link HttpContentType}
 * 
 * @author robson-sanchez
 */
public class HttpContentTypeTest {

  @Test
  public void testValues() {
    assertEquals("application/json", HttpContentType.APPLICATION_JSON.getValue());
    assertEquals("application/xml", HttpContentType.APPLICATION_XML.getValue());
    assertEquals("application/x-www-form-urlencoded", HttpContentType.FORM_URL_ENCODED.getValue());
    assertEquals("text/plain", HttpContentType.TEXT_PLAIN.getValue());
  }
  
}
