package net.open.payment.event.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.event.event
 * User: dean
 * Date: 2020/06/01
 * Time: 5:32 오후
 */

/**
 * 계좌이체 실패시 event
 */

@Getter
@Builder
@ToString
public class TransferDeniedEvent {
  private String srcAccountId;
  private String dstAccountId;
  private String transferId;
  private Long amount;
  private String description;
}
