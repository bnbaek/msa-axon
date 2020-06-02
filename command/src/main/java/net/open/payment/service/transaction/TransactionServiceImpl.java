package net.open.payment.service.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.advice.exception.NotFoundHolderException;
import net.open.payment.commands.DepositMoneyCommand;
import net.open.payment.commands.WithdrawMoneyCommand;
import net.open.payment.dto.DepositDto;
import net.open.payment.dto.WithdrawDto;
import net.open.payment.query.HolderQueryDto;
import net.open.payment.service.holder.HolderQueryService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 계좌의 입금과 출금 transaction을(를) 관리한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
  private final CommandGateway commandGateway;
  private final HolderQueryService holderQueryService;

  /**
   * 계좌에서  입금을 한다.
   */
  @Override
  public CompletableFuture<String> depositMoney(@Valid @RequestBody DepositDto depositDto) {

    //validation
    List<HolderQueryDto.Response> holders = holderQueryService.queryGetHolderById(depositDto.getHolderId());
    if (holders.get(0) == null) {
      throw new NotFoundHolderException(depositDto.getHolderId());
    }

    String transactionId = UUID.randomUUID().toString();

    //입금이변트를 실행한다.
    commandGateway.send(DepositMoneyCommand.builder()
        .transactionId(transactionId)
        .accountId(depositDto.getAccountId())
        .holderId(depositDto.getHolderId())
        .amount(depositDto.getAmount()).build());

    return CompletableFuture.supplyAsync(() -> transactionId);
  }

  /**
   * 계좌에서 출금을 한다.
   */
  @Override
  public CompletableFuture<String> withdrawMoney(@Valid @RequestBody WithdrawDto withdrawDto) {

    //validation
    List<HolderQueryDto.Response> holders = holderQueryService.queryGetHolderById(withdrawDto.getHolderId());
    if (holders.get(0) == null) {
      throw new NotFoundHolderException(withdrawDto.getHolderId());
    }

    String transactionId = UUID.randomUUID().toString();

    //출금이변트를 실행한다.
    commandGateway.send(WithdrawMoneyCommand.builder()
        .transactionId(transactionId)
        .accountId(withdrawDto.getAccountId())
        .holderId(withdrawDto.getHolderId())
        .amount(withdrawDto.getAmount()).build());

    return CompletableFuture.supplyAsync(() -> transactionId);
  }

}
