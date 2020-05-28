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
public class AccountCreationCommand {
  @TargetAggregateIdentifier
  private String holderId;
  private String accountId;

  @Builder
  public AccountCreationCommand(String holderId) {
    this.holderId = holderId;
    this.accountId = UUID.randomUUID().toString();
  }
}
