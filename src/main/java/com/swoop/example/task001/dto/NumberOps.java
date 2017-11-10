package com.swoop.example.task001.dto;

import java.util.ArrayList;

//POJO container for number operations.
public class NumberOps {
  private String m_parameter_1;
  private String m_parameter_2;
  private String m_operation;
  private String m_total;
  private ArrayList<String> m_error_codes = new ArrayList<String>();
  private String m_status;
 
  
  public NumberOps(){
  }

  public String getParameter_1() {
    return m_parameter_1;
  }

  public void setParameter_1(String m_parameter_1) {
    this.m_parameter_1 = m_parameter_1;
  }

  public String getOperation() {
    return m_operation;
  }

  public void setOperation(String m_operation) {
    this.m_operation = m_operation;
  }

  public String getParameter_2() {
    return m_parameter_2;
  }

  public void setParameter_2(String m_parameter_2) {
    this.m_parameter_2 = m_parameter_2;
  }

  public ArrayList<String> getError_codes() {
    return m_error_codes;
  }
  
  public void addErrorCode(String i_error_code) {
    if (i_error_code != null) m_error_codes.add(i_error_code);
  }
  
  public boolean isErrorFound(){
    if (m_error_codes.size() > 0) return true;
    return false;
  }

  public String getTotal() {
    return m_total;
  }

  public void setTotal(String m_total) {
    this.m_total = m_total;
  }

  public String getStatus() {
    return m_status;
  }

  public void setStatus(String m_status) {
    this.m_status = m_status;
  }
}
