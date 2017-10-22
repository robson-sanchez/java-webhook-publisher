package com.rvvsanchez.libs.event.publisher.webhook.http;

/**
 * Represents HTTP status code.
 * 
 * @author robson-sanchez
 */
public enum HttpStatus {
  
  // 1xx Informational
  CONTINUE(100, "Continue", HttpFamily.INFORMATIONAL),
  SWITCHING_PROTOCOLS(101, "Switching Protocols", HttpFamily.INFORMATIONAL),
  PROCESSING(102, "Processing", HttpFamily.INFORMATIONAL),
  
  // 2xx Success
  OK(200, "OK", HttpFamily.SUCCESS),
  CREATED(201, "Created", HttpFamily.SUCCESS),
  ACCEPTED(202, "Accepted", HttpFamily.SUCCESS),
  NON_AUTHORITATIVE_INFO(203, "Non-authoritative Information", HttpFamily.SUCCESS),
  NO_CONTENT(204, "No Content", HttpFamily.SUCCESS),
  RESET_CONTENT(205, "Reset Content", HttpFamily.SUCCESS),
  PARTIAL_CONTENT(206, "Partial Content", HttpFamily.SUCCESS),
  MULTI_STATUS(207, "Multi-Status", HttpFamily.SUCCESS),
  ALREADY_REPORTED(208, "Already Reported", HttpFamily.SUCCESS),
  
  // 3xx Redirection
  MULTIPLE_CHOICES(300, "Multiple Choices", HttpFamily.REDIRECTION),
  MOVED_PERMANENTLY(301, "Moved Permanently", HttpFamily.REDIRECTION),
  FOUND(302, "Found", HttpFamily.REDIRECTION),
  SEE_OTHER(303, "See Other", HttpFamily.REDIRECTION),
  NOT_MODIFIED(304, "Not Modified", HttpFamily.REDIRECTION),
  USE_PROXY(305, "Use Proxy", HttpFamily.REDIRECTION),
  TEMPORARY_REDIRECT(307, "Temporary Redirect", HttpFamily.REDIRECTION),
  PERMANENT_REDIRECT(308, "Permanent Redirect", HttpFamily.REDIRECTION);
  
  // 4xx Client Error
  
  // 5xx Server Error
  
  private int code;
  
  private String message;
  
  private HttpFamily family;
  
  private HttpStatus(int code, String message, HttpFamily family) {
    this.code = code;
    this.message = message;
    this.family = family;
  }
  
  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpFamily getFamily() {
    return family;
  }

  public boolean isInformational() {
    return HttpFamily.INFORMATIONAL.equals(family);
  }
  
  public boolean isSuccess() {
    return HttpFamily.SUCCESS.equals(family);
  }
  
  public boolean isRedirection() {
    return HttpFamily.REDIRECTION.equals(family);
  }
  
  public boolean isClientError() {
    return HttpFamily.CLIENT_ERROR.equals(family);
  }
  
  public boolean isServerError() {
    return HttpFamily.SERVER_ERROR.equals(family);
  }
  
  public enum HttpFamily {
    INFORMATIONAL,
    SUCCESS,
    REDIRECTION,
    CLIENT_ERROR,
    SERVER_ERROR
  }
  
}
