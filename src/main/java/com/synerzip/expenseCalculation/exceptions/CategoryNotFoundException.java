package com.synerzip.expenseCalculation.exceptions;

public class CategoryNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CategoryNotFoundException(String message) {
    super(message);

  }

  public CategoryNotFoundException(String message, Throwable cause) {
    super(message, cause);

  }

  public CategoryNotFoundException(Throwable cause) {
    super(cause);
  }


}
