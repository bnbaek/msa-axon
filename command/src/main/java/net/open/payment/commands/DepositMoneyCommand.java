package net.open.payment.commands;

import lombok.*;
import net.open.payment.TransactionType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

/**
 * Created by BNBAEK
 * Package : net.open.payment.commands
 * User: dean
 * Date: 2020/05/26
 * Time: 5:06 오후
 */
@Getter
@ToString
public class DepositMoneyCommand extends BaseCommand<String> {
  private String holderId;
  private Long amount;
  private TransactionType type;
  private String transactionId;


  @Builder
  public DepositMoneyCommand( String accountId, String holderId, Long amount) {
    super(accountId);
    this.holderId = holderId;
    this.amount = amount;
    this.transactionId = UUID.randomUUID().toString();
    this.type = TransactionType.DEPOSIT;
  }
}
