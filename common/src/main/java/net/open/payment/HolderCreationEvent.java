package net.open.payment;

import lombok.*;
import org.axonframework.serialization.Revision;

@Getter
@ToString
@Revision("1.0")
public class HolderCreationEvent {

  private String holderId;
  private String holderName;
  private String phoneNumber;
  private String address;
  private String userId;

  @Builder
  public HolderCreationEvent(String holderId, String holderName, String phoneNumber, String address, String userId) {
    this.holderId = holderId;
    this.holderName = holderName;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.userId = userId;
  }
}
