package net.open.payment.service.transfer;

import net.open.payment.aggregate.TransferDto;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/06/02
 * Time: 10:20 오전
 */
public interface TransferService {
  String transferMoney(TransferDto transferDto);
}
