package net.open.payment.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.AccountCreationEvent;
import net.open.payment.DepositMoneyEvent;
import net.open.payment.WithdrawMoneyEvent;
import net.open.payment.commands.AccountCreationCommand;
import net.open.payment.commands.DepositMoneyCommand;
import net.open.payment.commands.WithdrawMoneyCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/05/26
 * Time: 5:42 오후
 */
@RequiredArgsConstructor
@Aggregate
@Slf4j
public class AccountAggregate {
  @AggregateIdentifier
  private String accountId;
  private String holderId;
  private Long balance;

  @CommandHandler
  public AccountAggregate(AccountCreationCommand command) {
    log.debug("handling {}", command);
    apply(AccountCreationEvent.builder()
        .accountId(command.getId())
        .holderId(command.getHolderId())
        .build()
    );
  }

  @EventSourcingHandler
  protected void createAccount(AccountCreationEvent event) {
    log.debug("applaying {}", event);
    this.accountId = event.getAccountId();
    this.holderId = event.getHolderId();
    this.balance = 0L;
  }

  @CommandHandler
  protected void depositMoney(DepositMoneyCommand command) {
    log.debug("DepositMoneyCommand handling {}", command);
    if (command.getAmount() <= 0) throw new IllegalStateException("amount <= 0");
    apply(DepositMoneyEvent.builder()
        .transactionId(command.getTransactionId())
        .accountId(command.getId())
        .holderId(command.getHolderId())
        .amount(command.getAmount())
        .type(command.getType())
        .build());
  }

  @EventSourcingHandler
  protected void depositMoney(DepositMoneyEvent event) {
    log.debug("applaying {}", event);
    this.balance += event.getAmount();
    log.info("[balance] {}", this.balance);
  }

  @CommandHandler
  protected void withdrawMoney(WithdrawMoneyCommand command) {
    log.debug("handling {}", command);
    if (this.balance - command.getAmount() < 0) throw new IllegalStateException("잔고가 부족합니다.");
    if (command.getAmount() <= 0) throw new IllegalStateException("amount <= 0");
    apply(WithdrawMoneyEvent.builder()
        .transactionId(command.getTransactionId())
        .accountId(command.getId())
        .holderId(command.getHolderId())
        .amount(command.getAmount())
        .type(command.getType())
        .build());
  }

  @EventSourcingHandler
  protected void withdrawMoney(WithdrawMoneyEvent event) {
    log.debug("applaying {}", event);
    this.balance -= event.getAmount();
    log.info("[balance] {}", this.balance);
  }

//  @CommandHandler
//  protected void withdrawMoney(WithdrawMoneyCommand command) {
//    log.debug("withdrawMoney handling {}", command);
//    if (this.balance - command.getAmount() < 0) throw new IllegalStateException("잔고가 부족합니다.");
//    else if (command.getAmount() <= 0) throw new IllegalStateException("amount <= 0");
//    apply(WithdrawMoneyEvent.builder()
//        .transactionId(command.getTransactionId())
//        .accountId(command.getAccountId())
//        .holderId(command.getHolderId())
//        .amount(command.getAmount())
//        .type(command.getType())
//
//    );
//  }
//
//  @EventSourcingHandler
//  protected void withdrawMoney(WithdrawMoneyEvent event) {
//    log.debug("applaying {}", event);
//    this.balance -= event.getAmount();
//    log.info("[balance] {}", this.balance);
//  }


}
