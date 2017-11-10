package com.swoop.example.task001.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swoop.example.task001.dto.SITATime;
import com.swoop.example.task001.statics.AppStatics;

//Service class to perform SITA REST operations.
@Service
@Component
public class SITAService {
  
  
  // Service method to call external service 
  public SITATime getTime(SITATime i_sita_time, String i_aireport_code){
    i_sita_time.setAirportCode(i_aireport_code);
    
    HttpURLConnection t_conn = null;
    
    try {
      // Get current date
      DateFormat t_date_format = new SimpleDateFormat("yyyyMMdd");
      Date t_cur_date = new Date();
      
      // Construct URL and params external call
      UriComponents t_uri_cmp = UriComponentsBuilder.newInstance().
                                scheme("http").
                                host("localhost").
                                port("8080").
                                path("/waittime/v1/current/" + i_aireport_code).
                                query("operationDate={t_today_yyymmdd}").
                                buildAndExpand(t_date_format.format(t_cur_date));

      URL url = new URL(t_uri_cmp.toUriString());
      t_conn = (HttpURLConnection) url.openConnection();
      
      t_conn.setRequestMethod("GET");
      t_conn.setRequestProperty("Accept", "application/json");
      t_conn.setRequestProperty("X-apiKey", AppStatics.X_API_KEY); 
      
      // SITA Rest Call and process response
      if (t_conn.getResponseCode() != 200) {
        i_sita_time.setStatus(AppStatics.STS_FAILED);
        i_sita_time.setErrorType(AppStatics.ERR_TYPE_COMM_EX);
        
        // Add Comm errors to local REST response
        String t_json_err_msg = "{\"status\" : \"failed\", \"httperrorCode\" : " + t_conn.getResponseCode() + " }";
        
        ObjectMapper t_mapper = new ObjectMapper();
        JsonNode t_node = t_mapper.readTree(t_json_err_msg);
        
        i_sita_time.setSourceResponse(t_node); 
      } else {
        // Process response if SITA REST call responded
        BufferedReader br = new BufferedReader(new InputStreamReader((t_conn.getInputStream())));
        
        String t_output;
        String t_response_msg = "";
        while ((t_output = br.readLine()) != null) t_response_msg += t_output;
        
        ObjectMapper t_mapper = new ObjectMapper();
        JsonNode t_response_msg_node = t_mapper.readTree(t_response_msg);
        JsonNode t_status_node = t_response_msg_node.findValue("success"); 
        
        // Add SITA response to local REST response
        i_sita_time.setSourceResponse(t_response_msg_node);
       
        // Process SITA response
        if (t_status_node.asBoolean() == false){
          i_sita_time.setStatus(AppStatics.STS_FAILED);
          i_sita_time.setErrorType(AppStatics.ERR_TYPE_SITA_EX);
        } else if (t_status_node.asBoolean() == true) {
          i_sita_time.setStatus(AppStatics.STS_SUCCESS);
          
          //Extract Times and Zone
          JsonNode t_current_node = t_response_msg_node.findValue("current");
          
          ZonedDateTime t_datetime = ZonedDateTime.parse(t_current_node.get(0).findValue("localTime").asText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);            
          ZoneId  t_zone = t_datetime.getZone(); 
          
          i_sita_time.setZone(t_zone.getId());
          i_sita_time.setLocalTime(t_current_node.get(0).findValue("localTime").asText());
          i_sita_time.setCurrentTime(t_current_node.get(0).findValue("time").asText());
        } // end if 
      } // end if
    } catch (Exception ex){
      i_sita_time.setStatus(AppStatics.STS_FAILED);
      i_sita_time.setErrorType(AppStatics.ERR_TYPE_JAVA_EX);
      
      // Add Java errors to local REST response
      String t_json_err_msg = "{\"status\" : \"failed\", \"javaex\" : " + ex.getMessage() + " }";
      
      ObjectMapper t_mapper = new ObjectMapper();
      try {
        JsonNode t_node = t_mapper.readTree(t_json_err_msg);
        i_sita_time.setSourceResponse(t_node); 
      } catch (Exception ex_rt){
        ex_rt.printStackTrace(); // This should go into logs
      } // end try 
      
      ex.printStackTrace(); // This should go into logs
    } finally {
      if (t_conn != null) t_conn.disconnect();
    } // end try 
    
    // Return response
    return i_sita_time;
  } // end method (getTime)
  
} // end class (SITAService)
