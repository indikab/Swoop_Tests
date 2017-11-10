package com.swoop.example.task001.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.swoop.example.task001.dto.NumberOps;
import com.swoop.example.task001.dto.SITATime;

// Swoop App POJO Factory
@Configuration
public class SwoopAppPOJOFactory {
  
  // Return an instance of NumberOps POJO 
  @Bean
  @Scope("prototype")
  public NumberOps getNumberOpsInstance(){
    return new NumberOps();
  } // end method (getNumberOpsInstance)
  
  // Return an instance of SITATime POJO 
  @Bean
  @Scope("prototype")
  public SITATime getSITATimeInstance(){
    return new SITATime();
  } // end method (getSITATimeInstance)
} // end class (SwoopAppPOJOFactory)
