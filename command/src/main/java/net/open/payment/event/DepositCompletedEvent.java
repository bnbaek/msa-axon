package net.open.payment.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.event
 * User: dean
 * Date: 2020/05/27
 * Time: 6:31 오후
 */
@AllArgsConstructor
@ToString
@Getter
public class DepositCompletedEvent {
  private String accountID;
  private String transferID;
}