package net.open.payment.commands;

import net.open.payment.transfer.factory.AbstractTransferCommand;

/**
 * Created by BNBAEK
 * Package : net.open.payment.transfer.factory
 * User: dean
 * Date: 2020/06/01
 * Time: 5:26 오후
 */
public class MasterTransferCommand extends AbstractTransferCommand {
  @Override
  public String toString() {
    return "MasterTransferCommand{" +
        "srcAccountId='" + srcAccountId + '\'' +
        ", dstAccountId='" + dstAccountId + '\'' +
        ", amount=" + amount +
        ", transferId='" + transferId + '\'' +
        '}';
  }
}
