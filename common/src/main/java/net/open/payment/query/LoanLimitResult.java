package net.open.payment.query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.query
 * User: dean
 * Date: 2020/06/01
 * Time: 3:32 오후
 */

@Getter
@ToString
public class LoanLimitResult {

  private String holderId;
  private String bankName;
  private Long balance;
  private Long loanLimit;

  @Builder
  public LoanLimitResult(String holderId, String bankName, Long balance, Long loanLimit) {
    this.holderId = holderId;
    this.bankName = bankName;
    this.balance = balance;
    this.loanLimit = loanLimit;
  }
}
