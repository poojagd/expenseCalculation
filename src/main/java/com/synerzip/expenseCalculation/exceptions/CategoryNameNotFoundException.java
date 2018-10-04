package com.synerzip.expenseCalculation.exceptions;


public class CategoryNameNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  public CategoryNameNotFoundException(String message) {
    super(message);

  }

  public CategoryNameNotFoundException(String message, Throwable cause) {
    super(message, cause);

  }

  public CategoryNameNotFoundException(Throwable cause) {
    super(cause);
  }

}
