package com.swoop.example.task001.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.swoop.example.task001.config.SwoopAppPOJOFactory;
import com.swoop.example.task001.dto.NumberOps;
import com.swoop.example.task001.statics.AppStatics;

//Service class to perform math operations.
@Service
@Component
public class MathService {
  // Get access to POJO factory
  private static ApplicationContext m_pojo_factory = new AnnotationConfigApplicationContext(SwoopAppPOJOFactory.class);
  
  // Common method to handle all math operations. 
  // In future it can be expanded to other operation.
  public NumberOps processNumbers(NumberOps i_number_ops){
    
    //Validate input params and exit if there any errors.
    if (i_number_ops == null){
      NumberOps r_num_op = m_pojo_factory.getBean(NumberOps.class);
      r_num_op.setStatus(AppStatics.ERR_NULL_OBJ);
      return r_num_op;
    } else {
      
     // Check for operation type and call methods accordingly.
     if (i_number_ops.getOperation().compareToIgnoreCase(AppStatics.MATH_OP_ADD) == 0){
       return addNumbers(i_number_ops);
     } else {
       i_number_ops.setStatus(AppStatics.ERR_BAD_OP);
       return i_number_ops;
     } // end if 
    } // end if
  } // end method (processNumbers)
  
  // Common method for operation type ADD.
  private NumberOps addNumbers(NumberOps i_number_ops){
    Double t_p1 = 0.00;
    Double t_p2 = 0.00;
    try {
      if (i_number_ops.getParameter_1() == null){
        i_number_ops.addErrorCode(AppStatics.ERR_P1_001);
      } else {
        t_p1 = MathService.convertSTR2DBL(i_number_ops.getParameter_1());
      } // end if 
    } catch (Exception ex){
      i_number_ops.addErrorCode(AppStatics.ERR_P1_002);
    } // end try
    
    try {
      if (i_number_ops.getParameter_2() == null){
        i_number_ops.addErrorCode(AppStatics.ERR_P2_001);
      } else {
        t_p2 = MathService.convertSTR2DBL(i_number_ops.getParameter_2());
      } // end if 
    } catch (Exception ex){
      i_number_ops.addErrorCode(AppStatics.ERR_P2_002);
    } // end try
    
    if (!i_number_ops.isErrorFound()){
      i_number_ops.setTotal(new Double(t_p1 + t_p2).toString());
      i_number_ops.setStatus(AppStatics.STS_SUCCESS);
    } else {
      i_number_ops.setStatus(AppStatics.STS_FAILED);
    } // end if
    
    return i_number_ops;
  } // end method (addNumbers)
  
  // Common method to convert string to double
  private static Double convertSTR2DBL(String i_str_number) throws Exception {
    return Double.valueOf(i_str_number);
  } // end method (convertSTR2DBL)
} // end class (MathService)
