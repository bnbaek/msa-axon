package net.open.payment.service;

import lombok.RequiredArgsConstructor;
import net.open.payment.commands.AccountCreationCommand;
import net.open.payment.commands.DepositMoneyCommand;
import net.open.payment.commands.HolderCreationCommand;
import net.open.payment.commands.WithdrawMoneyCommand;
import net.open.payment.dto.AccountDto;
import net.open.payment.dto.DepositDto;
import net.open.payment.dto.HolderDto;
import net.open.payment.dto.WithdrawDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/27
 * Time: 11:03 오전
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final CommandGateway commandGateway;


  @Override
  public CompletableFuture<String> createHolder(HolderDto.Base holderDto) {
    return commandGateway.send(HolderCreationCommand.builder()
        .holderId(UUID.randomUUID().toString())
        .holderName(holderDto.getHolderName())
        .address(holderDto.getAddress())
        .phoneNumber(holderDto.getPhoneNumber())
        .userId(holderDto.getUserId())
        .build());
  }

  @Override
  public CompletableFuture<String> createAccount(AccountDto.Base accountDto) {
    return commandGateway.send(AccountCreationCommand.builder()
        .accountId(UUID.randomUUID().toString())
        .holderId(accountDto.getHolderId())
        .build());
  }

  @Override
  public CompletableFuture<String> depositMoney(DepositDto depositDto) {
    return commandGateway.send(DepositMoneyCommand.builder()
        .accountId(depositDto.getAccountId())
        .holderId(depositDto.getHolderId())
        .amount(depositDto.getAmount()).build());
  }

  @Override
  public CompletableFuture<String> withdrawMoney(WithdrawDto withdrawDto) {
    return commandGateway.send(WithdrawMoneyCommand.builder()
        .accountId(withdrawDto.getAccountId())
        .holderId(withdrawDto.getHolderId())
        .amount(withdrawDto.getAmount()).build());

  }
}
