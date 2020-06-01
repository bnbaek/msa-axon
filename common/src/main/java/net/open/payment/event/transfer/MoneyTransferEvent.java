package net.open.payment.event.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.open.payment.transfer.factory.TransferComamndFactory;

/**
 * Created by BNBAEK
 * Package : net.open.payment.event.event
 * User: dean
 * Date: 2020/06/01
 * Time: 5:29 오후
 */

/**
 * 계좌요청이 들어오면 인층계좌번호(srcAccountID) 및 입금 계좌 (dstAccountID)
 */

@Builder
@Getter
@ToString
public class MoneyTransferEvent {
  private String dstAccountId;
  private String srcAccountId;
  private Long amount;
  private String transferId;
  private TransferComamndFactory comamndFactory;
}
