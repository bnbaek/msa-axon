package net.open.payment.aggregate;

import net.open.payment.event.HolderCreationEvent;
import net.open.payment.commands.HolderCreationCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Created by iopenu@gmail.com on 2020/05/31
 * Github : https://github.com/bnbaek
 */
class HolderAggregateUnitTest {

  private FixtureConfiguration<HolderAggregate> fixture;

  @BeforeEach
  public void init() {
    fixture = new AggregateTestFixture<>(HolderAggregate.class);
  }

  @Test
  void giveHolderPublish() {
    String holderId = UUID.randomUUID().toString();
    String address = "address";
    String holderName = "kelly";
    String userId = "kelly@daum.net";
    String phoneNumber = "01022223333";
    fixture.givenNoPriorActivity()
        .when(HolderCreationCommand.builder()
            .holderId(holderId)
            .address(address)
            .holderName(holderName)
            .userId(userId)
            .phoneNumber(phoneNumber)
            .build())
        .expectEvents(new HolderCreationEvent(holderId, holderName, phoneNumber, address, userId))
    ;
  }


  @Test
  void givenHolderPublish() {
    String holderId = UUID.randomUUID().toString();
    String address = "address";
    String holderName = "kelly";
    String userId = "kelly@daum.net";
    String phoneNumber = "01022223333";
    fixture
        .given(HolderCreationEvent.builder()
            .holderId(holderId)
            .address(address)
            .holderName(holderName)
            .phoneNumber(phoneNumber)
            .userId(userId)
            .build())
        .when(HolderCreationCommand.builder()
            .holderId(holderId)
            .address(address)
            .holderName(holderName)
            .userId(userId)
            .phoneNumber(phoneNumber)
            .build())
        .expectEvents(new HolderCreationEvent(holderId, holderName, phoneNumber, address, userId))
    ;
  }


}