package net.open.payment.service.transaction;

import net.open.payment.aggregate.TransferDto;
import net.open.payment.dto.*;

import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/27
 * Time: 11:01 오전
 */
public interface TransactionService {

  CompletableFuture<String> depositMoney(DepositDto depositDto);

  CompletableFuture<String> withdrawMoney(WithdrawDto withdrawDto);
}
