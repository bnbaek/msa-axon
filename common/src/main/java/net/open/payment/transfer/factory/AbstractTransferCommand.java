package net.open.payment.transfer.factory;

import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Created by BNBAEK
 * Package : net.open.payment.transfer.factory
 * User: dean
 * Date: 2020/05/29
 * Time: 10:52 오전
 */
@ToString
@Getter
public abstract class AbstractTransferCommand {
  @TargetAggregateIdentifier
  protected String srcAccountId;
  protected String dstAccountId;
  protected Long amount;
  protected String transferId;

  public AbstractTransferCommand create(String srcAccountId, String dstAccountId, Long amount, String transferId) {
    this.srcAccountId = srcAccountId;
    this.dstAccountId = dstAccountId;
    this.amount = amount;
    this.transferId = transferId;
    return this;
  }

}
