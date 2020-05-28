package net.open.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment
 * User: dean
 * Date: 2020/05/26
 * Time: 5:03 오후
 */
@ToString
@Getter
public class AccountCreationEvent {
  private String holderId;
  private String accountId;

  @Builder
  public AccountCreationEvent(String holderId, String accountId) {
    this.holderId = holderId;
    this.accountId = accountId;
  }
}
