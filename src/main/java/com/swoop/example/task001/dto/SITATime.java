package com.swoop.example.task001.dto;

import com.fasterxml.jackson.databind.JsonNode;

//POJO container for SITA time operations.
public class SITATime {
  private String m_airport_code;
  private String m_status;
  private String m_current_time;
  private String m_local_time;
  private String m_zone;
  private JsonNode m_source_response;
  private String m_error_type;
  
  public String getAirportCode() {
    return m_airport_code;
  }
  
  public void setAirportCode(String m_airport_code) {
    this.m_airport_code = m_airport_code;
  }
  
  public String getStatus() {
    return m_status;
  }
  
  public void setStatus(String m_status) {
    this.m_status = m_status;
  }
  
  public String getCurrentTime() {
    return m_current_time;
  }
  
  public void setCurrentTime(String m_current_time) {
    this.m_current_time = m_current_time;
  }
  
  public String getLocalTime() {
    return m_local_time;
  }
  
  public void setLocalTime(String m_local_time) {
    this.m_local_time = m_local_time;
  }
  
  public JsonNode getSourceResponse() {
    return m_source_response;
  }
  
  public void setSourceResponse(JsonNode m_source_response) {
    this.m_source_response = m_source_response;
  }

  public String getZone() {
    return m_zone;
  }

  public void setZone(String m_zone) {
    this.m_zone = m_zone;
  }

  public String getErrorType() {
    return m_error_type;
  }

  public void setErrorType(String m_error_type) {
    this.m_error_type = m_error_type;
  }
}
