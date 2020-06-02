package net.open.payment.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.event.transfer.TransferApprovedEvent;
import net.open.payment.event.transfer.TransferDeniedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 5:53 오후
 */
@RequiredArgsConstructor
@Aggregate
@Slf4j
@Entity
public class AccountAppleAggregate {
  @AggregateIdentifier
  @Id
  private String accountId;
  private Long balance;

  @CommandHandler
  public AccountAppleAggregate(AccountCreationCommand command) throws IllegalAccessException {
    log.debug("handling {}", command);
    if (command.getBalance() <= 0)
      throw new IllegalAccessException("유효하지 않은 입력입니다.");
    apply(new AccountCreationCommand(command.getAccountId(), command.getBalance()));
  }

  @EventSourcingHandler
  protected void on(AccountCreationEvent event) {
    log.debug("event {}", event);
    this.accountId = event.getAccountID();
    this.balance = event.getBalance();
  }

  @CommandHandler
  protected void on(AppleBankTransferCommand command) throws InterruptedException {

    log.debug("handling {}", command);
    if (this.balance < command.getAmount()) {
      apply(TransferDeniedEvent.builder()
          .srcAccountId(command.getSrcAccountId())
          .dstAccountId(command.getDstAccountId())
          .amount(command.getAmount())
          .description("잔고가 부족합니다.")
          .transferId(command.getTransferId())
          .build());

    } else {
      apply(TransferApprovedEvent.builder()
          .srcAccountId(command.getSrcAccountId())
          .dstAccountId(command.getDstAccountId())
          .transferId(command.getTransferId())
          .amount(command.getAmount())
          .build());
    }
  }

  @EventSourcingHandler
  protected void on(TransferApprovedEvent event) {
    log.debug("event {}", event);
    this.balance -= event.getAmount();
  }

}
