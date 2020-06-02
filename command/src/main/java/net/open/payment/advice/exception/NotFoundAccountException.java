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
public class NotFoundAccountException extends RuntimeException {

  private final static String errorMsg = "일치하는 계좌을(를) 찾을수 없습니다.";

  private String accountId;
  private AccountTransferType type = AccountTransferType.ETC;

  public NotFoundAccountException(String accountId) {
    super(errorMsg);
    this.accountId = accountId;
  }

  public NotFoundAccountException(String accountId, AccountTransferType type) {
    super(errorMsg);
    this.accountId = accountId;
    this.type = type;
  }


  public enum AccountTransferType {
    DEPOSIT, WITHDRAW, ETC
  }
}
