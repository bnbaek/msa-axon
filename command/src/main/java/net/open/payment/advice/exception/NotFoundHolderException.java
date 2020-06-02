package net.open.payment.advice.exception;

import lombok.Getter;

/**
 * Created by BNBAEK
 * Package : net.open.payment.advice.exception
 * User: dean
 * Date: 2020/06/02
 * Time: 4:03 오후
 */
@Getter
public class NotFoundHolderException extends RuntimeException {

  private final static String errorMsg = "일치하는 계정을(를) 찾을수 없습니다.";

  private String holderId;

  public NotFoundHolderException(String holderId) {
    this.holderId = holderId;
  }

}
