package net.open.payment.api;

import lombok.RequiredArgsConstructor;
import net.open.payment.aggregate.AccountAggregate;
import net.open.payment.aggregate.HolderAggregate;
import net.open.payment.dto.AccountDto;
import net.open.payment.dto.DepositDto;
import net.open.payment.dto.HolderDto;
import net.open.payment.dto.WithdrawDto;
import net.open.payment.service.TransactionQueryService;
import net.open.payment.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.api
 * User: dean
 * Date: 2020/05/27
 * Time: 11:21 오전
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;
  private final TransactionQueryService transactionQueryService;

  @PostMapping("holder")
  public CompletableFuture<String> createHolder(@RequestBody HolderDto.Base request) throws NoSuchFieldException {


    return transactionService.createHolder(request);
  }

  @PostMapping("account")
  public CompletableFuture<String> createAccount(@RequestBody AccountDto.Base request) {
    return transactionService.createAccount(request);
  }

  @PostMapping("deposit")
  public CompletableFuture<String> deposit(@RequestBody DepositDto request) {
    return transactionService.depositMoney(request);
  }

  @PostMapping("withdraw")
  public CompletableFuture<String> withdraw(@RequestBody WithdrawDto request) {
    return transactionService.withdrawMoney(request);
  }

}
