package net.open.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment
 * User: dean
 * Date: 2020/05/26
 * Time: 5:04 오후
 */
@Getter
@ToString
public class WithdrawMoneyEvent {
  private String transactionId;
  private String holderId;
  private String accountId;
  private Long amount;
  private TransactionType type;

  @Builder
  public WithdrawMoneyEvent(String transactionId, String holderId, String accountId, Long amount, TransactionType type) {
    this.transactionId = transactionId;
    this.holderId = holderId;
    this.accountId = accountId;
    this.amount = amount;
    this.type = type;
  }
}
