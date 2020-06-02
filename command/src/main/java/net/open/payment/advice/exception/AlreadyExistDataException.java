package net.open.payment.advice.exception;

import lombok.Getter;

/**
 * Created by BNBAEK
 * Package : net.open.payment.advice.exception
 * User: dean
 * Date: 2020/06/02
 * Time: 3:23 오후
 */
@Getter
public class AlreadyExistDataException extends RuntimeException {

  private final static String errorMsg = " 이미 존재하는 데이터 입니다.";

  private String id;

  public AlreadyExistDataException(String message, String id) {
    super(message);
    this.id = id;
  }

  public AlreadyExistDataException(String id) {
    super(errorMsg);
    this.id = id;
  }

}
