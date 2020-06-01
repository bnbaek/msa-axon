package net.open.payment.event.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.event.event
 * User: dean
 * Date: 2020/06/01
 * Time: 5:31 오후
 */

/**
 * 계좌이체 성공시 금액 변경 event
 */

@Builder
@ToString
@Getter
public class TransferApprovedEvent {
  private String srcAccountId;
  private String dstAccountId;
  private String transferId;
  private Long amount;
}
