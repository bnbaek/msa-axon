package net.open.payment.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.open.payment.HolderCreationEvent;
import net.open.payment.commands.HolderCreationCommand;
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
 * Time: 5:40 오후
 */
@Getter
@RequiredArgsConstructor
@Aggregate
public class HolderAggregate {
  @AggregateIdentifier
  private String holderId;
  private String holderName;
  private String phoneNumber;
  private String address;
  private String userId;

  //Aggregate에 대한 명령이 발생되었을 때 호출되는 메소드임을 알려주는 마커 역할
  @CommandHandler
  public HolderAggregate(HolderCreationCommand command) {
    apply(HolderCreationEvent.builder()
        .holderId(command.getId())
        .holderName(command.getHolderName())
        .phoneNumber(command.getPhoneNumber())
        .address(command.getAddress())
        .userId(command.getUserId())
        .build());
  }

  // CommandHandler에서 발생한 이벤트를 적용하는 메소드임을 알려주는 마커 역할
  @EventSourcingHandler
  protected void createAccount(HolderCreationEvent event) {
    this.holderId = event.getHolderId();
    this.holderName = event.getHolderName();
    this.phoneNumber = event.getPhoneNumber();
    this.address = event.getAddress();
    this.userId = event.getUserId();
  }
}
