package net.open.payment.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.open.payment.event.TransactionType;

import java.util.UUID;

/**
 * Created by BNBAEK
 * Package : net.open.payment.commands
 * User: dean
 * Date: 2020/05/26
 * Time: 5:09 오후
 */
@Getter
@ToString
public class WithdrawMoneyCommand extends BaseCommand<String> {
  private String holderId;
  private Long amount;
  private TransactionType type;
  private String transactionId;

  @Builder
  public WithdrawMoneyCommand(String accountId, String holderId, Long amount) {
    super(accountId);
    this.holderId = holderId;
    this.amount = amount;
    this.type = TransactionType.WITHDRAW;
    this.transactionId = UUID.randomUUID().toString();
  }
}
