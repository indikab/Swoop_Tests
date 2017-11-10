package com.swoop.example.task001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.swoop.example.task001")
public class SwoopMainApp {
    public static void main( String[] args )    {
        System.out.println( "Swoop World!" );
        
        @SuppressWarnings("unused")
        ApplicationContext app_ctx = SpringApplication.run(SwoopMainApp.class, args);
        
    } // end method(main)
     
} // end class(MainApp)