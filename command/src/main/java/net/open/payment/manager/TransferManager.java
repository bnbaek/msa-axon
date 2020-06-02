package net.open.payment.manager;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.aggregate.TransferApprovedCommand;
import net.open.payment.event.DepositCompletedEvent;
import net.open.payment.event.transfer.MoneyTransferEvent;
import net.open.payment.event.transfer.TransferApprovedEvent;
import net.open.payment.event.transfer.TransferDeniedEvent;
import net.open.payment.transfer.factory.TransferComamndFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by BNBAEK
 * Package : net.open.payment.manager
 * User: dean
 * Date: 2020/06/01
 * Time: 6:49 오후
 */
@Saga
@Slf4j
@NoArgsConstructor
public class TransferManager {

  @Autowired
  private transient CommandGateway commandGateway;
  private TransferComamndFactory comamndFactory;

  @StartSaga
  @SagaEventHandler(associationProperty = "transferId")
  protected void on(MoneyTransferEvent event) {
    log.info("계좌이체 시작");
    log.info("event : {}", event);
    comamndFactory = event.getComamndFactory();
    SagaLifecycle.associateWith("srcAccountId", event.getSrcAccountId());
    log.info("계좌 이체 시작 : {}", event);
    commandGateway.send(comamndFactory.getTransferCommand());
  }

  @SagaEventHandler(associationProperty = "srcAccountId")
  protected void on(TransferApprovedEvent event) {
    log.info("이체 금액 {} 계좌 반영 요청 : {}", event.getAmount(), event);
    SagaLifecycle.associateWith("accountId", event.getDstAccountId());
    commandGateway.send(TransferApprovedCommand.builder()
        .accountId(event.getDstAccountId())
        .amount(event.getAmount())
        .transferId(event.getTransferId())
        .build());

  }

  @SagaEventHandler(associationProperty = "srcAccountId")
  protected void on(TransferDeniedEvent event) {
    log.info("계좌 이체 실패 : {}", event);
    log.info("실패 사유 : {}", event.getDescription());

    //로그이벤트 추가
    SagaLifecycle.end();
  }

  @SagaEventHandler(associationProperty = "accountId")
  @EndSaga
  protected void on(DepositCompletedEvent event) {
    log.info("계좌 이체 성공 : {}", event);
    //로그이벤트 추가
  }

}
