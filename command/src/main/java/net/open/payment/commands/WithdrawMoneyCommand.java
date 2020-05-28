package net.open.payment.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.open.payment.TransactionType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

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
@NoArgsConstructor
public class WithdrawMoneyCommand {
  @TargetAggregateIdentifier
  private String accountId;
  private String holderId;
  private Long amount;
  private TransactionType type;
  private String transactionId;

  @Builder
  public WithdrawMoneyCommand(String accountId, String holderId, Long amount) {
    this.transactionId = UUID.randomUUID().toString();
    this.accountId = accountId;
    this.holderId = holderId;
    this.amount = amount;
    this.type = TransactionType.WITHDRAW;
  }
}
