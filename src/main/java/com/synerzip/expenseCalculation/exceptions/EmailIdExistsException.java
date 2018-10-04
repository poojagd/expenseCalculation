package com.synerzip.expenseCalculation.exceptions;

public class EmailIdExistsException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public EmailIdExistsException(String message) {
    super(message);

  }

  public EmailIdExistsException(String message, Throwable cause) {
    super(message, cause);

  }

  public EmailIdExistsException(Throwable cause) {
    super(cause);
  }
}
