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

  public void create(String srcAccountId, String dstAccountId, Long amount, String transferId) {
    transferCommand.create(srcAccountId, dstAccountId, amount, transferId);
  }

  public AbstractTransferCommand getTransferCommand() {
    return this.transferCommand;
  }
}
