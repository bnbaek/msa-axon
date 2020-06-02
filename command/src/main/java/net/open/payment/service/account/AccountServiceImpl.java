package net.open.payment.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.advice.exception.NotFoundHolderException;
import net.open.payment.commands.AccountCreationCommand;
import net.open.payment.dto.AccountDto;
import net.open.payment.query.HolderQueryDto;
import net.open.payment.service.holder.HolderQueryService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.account
 * User: dean
 * Date: 2020/06/02
 * Time: 5:56 오후
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
  private final CommandGateway commandGateway;
  private final HolderQueryService holderQueryService;

  /**
   * 계좌를 생성한다.
   */
  @Override
  public CompletableFuture<String> createAccount(AccountDto.Base accountDto) {

    //validation
    List<HolderQueryDto.Response> holders = holderQueryService.queryGetHolderById(accountDto.getHolderId());
    if (holders.get(0) == null) {
      throw new NotFoundHolderException(accountDto.getHolderId());
    }

    String accountId = UUID.randomUUID().toString();
    commandGateway.send(AccountCreationCommand.builder()
        .accountId(accountId)
        .holderId(accountDto.getHolderId())
        .build());

    return CompletableFuture.supplyAsync(() -> accountId);
  }
}
