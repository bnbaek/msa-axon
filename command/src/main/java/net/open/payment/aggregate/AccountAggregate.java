package net.open.payment.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.advice.exception.NotEnoughMoneyException;
import net.open.payment.commands.MasterTransferCommand;
import net.open.payment.event.*;
import net.open.payment.commands.AccountCreationCommand;
import net.open.payment.commands.DepositMoneyCommand;
import net.open.payment.commands.WithdrawMoneyCommand;
import net.open.payment.event.transfer.MoneyTransferEvent;
import net.open.payment.event.transfer.TransferApprovedEvent;
import net.open.payment.event.transfer.TransferDeniedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

  private final transient Random random = new Random();

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
    if (this.balance - command.getAmount() < 0) throw new NotEnoughMoneyException(command.getId(),command.getAmount(),this.balance);
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


  @CommandHandler
  protected void transferMoney(MoneyTransferCommand command) {
    log.debug("handling {}", command);
    apply(MoneyTransferEvent.builder()
        .srcAccountId(command.getSrcAccountId())
        .dstAccountId(command.getDstAccountId())
        .amount(command.getAmount())
        .comamndFactory(command.getBankType().getCommandFactory(command))
        .transferId(command.getTransferId())
        .build());
  }

  @CommandHandler
  protected void transferMoney(TransferApprovedCommand command) {
    log.debug("handling {}", command);
    apply(new DepositMoneyEvent(command.getTransferId(), this.holderId, command.getAccountId(), command.getAmount(), TransactionType.DEPOSIT));
    apply(new DepositCompletedEvent(command.getAccountId(), command.getTransferId()));
  }


  /**
   * transfer 받는차감
   * @param command
   * @throws InterruptedException
   */
  @CommandHandler
  protected void on(MasterTransferCommand command) throws InterruptedException {
    if (random.nextBoolean())
      TimeUnit.SECONDS.sleep(15);
    log.info("MasterTransferCommand handling {}", command);
    log.info("balance {}", this.balance);
    log.info("accountId {}", this.accountId);
    log.info("holderId {}", this.holderId);
    log.debug("handling {}", command);
    if (this.balance < command.getAmount()) {
      apply(TransferDeniedEvent.builder()
          .srcAccountId(command.getSrcAccountId())
          .dstAccountId(command.getDstAccountId())
          .amount(command.getAmount())
          .description("잔고가 부족합니다.")
          .transferId(command.getTransferId())
          .build()
      );
    } else {

      apply(WithdrawMoneyEvent.builder()
          .transactionId(command.getTransferId())
          .accountId(command.getSrcAccountId())
          .holderId(this.holderId)
          .amount(command.getAmount())
          .type(TransactionType.WITHDRAW)
          .build());

      apply(TransferApprovedEvent.builder()
          .srcAccountId(command.getSrcAccountId())
          .dstAccountId(command.getDstAccountId())
          .amount(command.getAmount())
          .transferId(command.getTransferId())
          .build()
      );
    }

  }

  @EventSourcingHandler
  protected void on(TransferApprovedEvent event) {
    log.debug("차감 event {}", event);
    log.debug("차감 {}", event.getAmount());
    this.balance -= event.getAmount();
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
