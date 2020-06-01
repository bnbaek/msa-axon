package net.open.payment.transfer.factory;

import lombok.RequiredArgsConstructor;

/**
 * Created by BNBAEK
 * Package : net.open.payment.transfer.factory
 * User: dean
 * Date: 2020/05/27
 * Time: 6:23 오후
 */
@RequiredArgsConstructor
public class TransferComamndFactory {
  private final AbstractTransferCommand transferCommand;

  public void create(String srcAccountID, String dstAccountID, Long amount, String transferID) {
    transferCommand.create(srcAccountID, dstAccountID, amount, transferID);
  }

  public AbstractTransferCommand getTransferCommand() {
    return this.transferCommand;
  }
}
