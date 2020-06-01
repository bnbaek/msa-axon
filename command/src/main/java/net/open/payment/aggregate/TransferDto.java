package net.open.payment.aggregate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 6:31 오후
 */
@Getter
@Setter
public class TransferDto {
  private String srcAccountId;
  private String dstAccountId;
  private Long amount;
  private MoneyTransferCommand.BankType bankType;


}
