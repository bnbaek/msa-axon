package net.open.payment.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
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
public class HolderCreationCommand extends BaseCommand<String> {
  private String holderName;
  private String phoneNumber;
  private String address;
  private String userId;

  @Builder
  public HolderCreationCommand(String holderId, String holderName, String phoneNumber, String address, String userId) {
    super(holderId);
    this.holderName = holderName;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.userId = userId;
  }
}
