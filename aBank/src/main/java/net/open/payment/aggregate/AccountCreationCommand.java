package net.open.payment.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 6:00 오후
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class AccountCreationCommand {
  @TargetAggregateIdentifier
  private String accountId;
  private Long balance;
}
