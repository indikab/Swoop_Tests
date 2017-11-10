package com.swoop.example.task001.ctrls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swoop.example.task001.config.SwoopAppPOJOFactory;
import com.swoop.example.task001.dto.SITATime;
import com.swoop.example.task001.services.SITAService;

//Rest controller for SITA time related services.
@RestController
public class TimeCtrl {
  // Get access to POJO factory
  private static ApplicationContext m_pojo_factory = new AnnotationConfigApplicationContext(SwoopAppPOJOFactory.class);  
  
  @Autowired
  SITAService t_sita_service;
  
  //http://<server_url>/time/now/yyc
  @GetMapping(value = "/time/now/{airport_code}", produces = "application/json") 
  public ResponseEntity<SITATime> checkTimeNowGET(@PathVariable("airport_code") String i_aireport_code,
                                                  @RequestParam Map<String,String> i_request_params){
    System.out.println("checkTimeNowGET"); // This should convert to Log4J logging
    
    // Create a SITATime instance to return at the end
    SITATime t_sita_time = m_pojo_factory.getBean(SITATime.class);
    
    t_sita_time = t_sita_service.getTime(t_sita_time, i_aireport_code);
    
    return new ResponseEntity<SITATime>(t_sita_time, HttpStatus.OK);
  } // end method (checkTimeNowGET)  
  
  //https://waittime.api.aero/waittime/v1/current/YYC?operationDate=20171106
  // Dummy method to return same results as SITA wait time
  @GetMapping(value = "/waittime/v1/current/{airport_code}", produces = "application/json") 
  public ResponseEntity<String> getSITADummy(@PathVariable("airport_code") String i_aireport_code,
                                             @RequestParam Map<String,String> i_request_params){
    System.out.println("getSITADummy"); // This should convert to Log4J logging
    
    System.out.println(i_request_params.get("operationDate")); // This should convert to Log4J logging
    
    @SuppressWarnings("unused")
    String i_bad = "{\"errors\": {     \"error\": [ { \"description\": \"no service available\", \"code\": \"1003\" } ] }, \"success\": false}";
    String i_good = "{ \"success\": true, \"current\": [{    \"airportCode\": \"YYC\",  \"airportName\": \"Calgary International Airport\",    \"queueId\": \"4dbe38e82f245f3ce8c347d6d29184df\", \"queueName\": \"South General\",  \"projectedWaitTime\": 960, \"projectedMinWaitMinutes\": 14, \"projectedMaxWaitMinutes\": 18, \"localTime\": \"2017-11-07T10:15:00.000-07:00\",  \"time\": \"2017-11-07T00:05:15.000Z\" }]}}";
    
    return new ResponseEntity<String>(i_bad, HttpStatus.OK);
  } // end method (getSITADummy)  

} // end class (TimeCtrl)
