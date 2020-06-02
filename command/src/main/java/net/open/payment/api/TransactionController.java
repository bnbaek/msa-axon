package net.open.payment.api;

import lombok.RequiredArgsConstructor;
import net.open.payment.dto.AccountDto;
import net.open.payment.dto.DepositDto;
import net.open.payment.dto.HolderDto;
import net.open.payment.dto.WithdrawDto;
import net.open.payment.service.test.TransactionQueryService;
import net.open.payment.service.transaction.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

  @PostMapping("deposit")
  public CompletableFuture<String> deposit(@Valid @RequestBody DepositDto request) {
    return transactionService.depositMoney(request);
  }

  @PostMapping("withdraw")
  public CompletableFuture<String> withdraw(@Valid @RequestBody WithdrawDto request) {
    return transactionService.withdrawMoney(request);
  }


}
