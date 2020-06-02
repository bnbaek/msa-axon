package net.open.payment.advice.exception;

import lombok.Getter;

@Getter
public class NotEnoughMoneyException extends RuntimeException {

  private final static String errorMsg = "잔고가 부족합니다.";

  private String accountId;
  private long requestAmount;
  private long possibleAmount;

  public NotEnoughMoneyException(String message, String accountId, long requestAmount, long possibleAmount) {
    super(message);
    this.requestAmount = requestAmount;
    this.possibleAmount = this.possibleAmount;
    this.accountId = accountId;
  }

  public NotEnoughMoneyException(String accountId, long requestAmount, long possibleAmount) {
    super(errorMsg);
    this.requestAmount = requestAmount;
    this.possibleAmount = this.possibleAmount;
    this.accountId = accountId;
  }
}