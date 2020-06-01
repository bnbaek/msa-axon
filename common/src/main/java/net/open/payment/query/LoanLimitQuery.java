package net.open.payment.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.query
 * User: dean
 * Date: 2020/06/01
 * Time: 3:30 오후
 */

@Getter
@ToString
public class LoanLimitQuery {
  private String holderId;
  private Long balance;

  public LoanLimitQuery(String holderId, Long balance) {
    this.holderId = holderId;
    this.balance = balance;
  }
}
