package com.swoop.example.task001.ctrls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swoop.example.task001.config.SwoopAppPOJOFactory;
import com.swoop.example.task001.dto.NumberOps;
import com.swoop.example.task001.services.MathService;

//Rest controller for Math related services.
@RestController
public class MathCtrl {
  // Get access to POJO factory
  private static ApplicationContext m_pojo_factory = new AnnotationConfigApplicationContext(SwoopAppPOJOFactory.class);
 
  @Autowired
  private MathService m_math; 
   
  //http://<server_url>/math/add?n1=<numeric param 1>&n2=<numeric param 2>
	@GetMapping(value = "/math/add", produces = "application/json") 
	public ResponseEntity<NumberOps> addMathGet(@RequestParam Map<String,String> i_request_params){
	  System.out.println("addMathGet"); // 
	  
	  // Create a NumberOps instance to return at the end
	  NumberOps t_num_ops = m_pojo_factory.getBean(NumberOps.class);
   
  	// Add input params
    t_num_ops.setOperation("ADD");
	
    t_num_ops.setParameter_1(i_request_params.get("p1"));
    t_num_ops.setParameter_2(i_request_params.get("p2"));
    
    // Perform math operation
    t_num_ops = m_math.processNumbers(t_num_ops);
    
    // Return results
    return new ResponseEntity<NumberOps>(t_num_ops, HttpStatus.OK);
  } // end method (addMathGet)  
 
  //http://<server_url>/math/add  (allow for form params 1&2 in a POST body)
  @PostMapping(value = "/math/add", produces = "application/json")
  public ResponseEntity<NumberOps> addMathPost(@RequestBody NumberOps i_num_ops){
    System.out.println("addMathPost");
   
    // Perform math operation
    i_num_ops = m_math.processNumbers(i_num_ops);
   
    // Return results
    return new ResponseEntity<NumberOps>(i_num_ops, HttpStatus.OK);
  } // end method (addMathPost)
} // end class (MathCtrl)
