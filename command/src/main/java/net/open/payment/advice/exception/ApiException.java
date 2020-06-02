package net.open.payment.advice.exception;

import lombok.Getter;

/**
 * Created by BNBAEK
 * Package : net.open.payment.advice.exception
 * User: dean
 * Date: 2020/06/02
 * Time: 2:41 오후
 */

@Getter
public class ApiException extends RuntimeException {

  private ApiResponseCode status;
  private String message;

  /**
   * constructor
   */
  public ApiException(ApiResponseCode status, Exception e) {
    super(e);
    this.status = status;
    this.message = status.getMessage();
  }

  /**
   * constructor
   */
  public ApiException(ApiResponseCode status, String message, Exception e) {
    super(e);
    this.status = status;
    this.message = message;
  }

  /**
   * constructor
   */
  public ApiException(ApiResponseCode status) {
    this.status = status;
    this.message = status.getMessage();
  }

  /**
   * constructor
   */
  public ApiException(ApiResponseCode status, String message) {
    this.status = status;
    this.message = message;
  }
}