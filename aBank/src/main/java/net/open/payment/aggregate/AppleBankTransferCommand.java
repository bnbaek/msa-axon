package net.open.payment.aggregate;

import net.open.payment.transfer.factory.AbstractTransferCommand;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 6:04 오후
 */
public class AppleBankTransferCommand extends AbstractTransferCommand {
  @Override
  public String toString() {
    return "AppleBankTransferCommand{" +
        "srcAccountId='" + srcAccountId + '\'' +
        ", dstAccountId='" + dstAccountId + '\'' +
        ", amount=" + amount +
        ", transferId='" + transferId + '\'' +
        '}';
  }
}
