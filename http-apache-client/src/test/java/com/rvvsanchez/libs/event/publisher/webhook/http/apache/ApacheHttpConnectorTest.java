package com.rvvsanchez.libs.event.publisher.webhook.http.apache;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.rvvsanchez.libs.event.publisher.webhook.http.HttpContentType;

/**
 * Unit tests for {@link ApacheHttpConnector}
 * 
 * @author robson-sanchez
 */
public class ApacheHttpConnectorTest {

  private ApacheHttpConnector connector = new ApacheHttpConnector();
  
  @Test
  public void testPostEvent() throws IOException {
    // FIXME
    assertEquals(0, connector.postEvent("", HttpContentType.APPLICATION_JSON, ""));
  }
  
}
