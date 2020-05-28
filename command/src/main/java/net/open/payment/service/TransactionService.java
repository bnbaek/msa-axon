package net.open.payment.service;

import net.open.payment.dto.AccountDto;
import net.open.payment.dto.DepositDto;
import net.open.payment.dto.HolderDto;
import net.open.payment.dto.WithdrawDto;

import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/27
 * Time: 11:01 오전
 */
public interface TransactionService {
  CompletableFuture<String> createHolder(HolderDto.Base holderDto);

  CompletableFuture<String> createAccount(AccountDto.Base accountDto);

  CompletableFuture<String> depositMoney(DepositDto depositDto);

  CompletableFuture<String> withdrawMoney(WithdrawDto withdrawDto);
}
