package net.open.payment.aggregate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 6:36 오후
 */
@ToString
@Getter
@Builder
public class TransferApprovedCommand {
  @TargetAggregateIdentifier
  private String accountId;
  private Long amount;
  private String transferId;
}
