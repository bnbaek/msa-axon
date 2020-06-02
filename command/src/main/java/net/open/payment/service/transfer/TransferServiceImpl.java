package net.open.payment.service.transfer;

import lombok.RequiredArgsConstructor;
import net.open.payment.advice.exception.NotEnoughMoneyException;
import net.open.payment.advice.exception.NotFoundAccountException;
import net.open.payment.aggregate.MoneyTransferCommand;
import net.open.payment.aggregate.TransferDto;
import net.open.payment.query.AccountQueryDto;
import net.open.payment.service.account.AccountQueryService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

/**
 * 송금을 관리한다.
 */
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final CommandGateway commandGateway;
  private final AccountQueryService accountQueryService;

  //송금한다.
  @Override
  public String transferMoney(TransferDto transferDto) {

    //송금전에 validation

    AccountQueryDto.Response srcAccount = accountQueryService.queryGetAccount(transferDto.getSrcAccountId());
    AccountQueryDto.Response dstAccount = accountQueryService.queryGetAccount(transferDto.getDstAccountId());

    transferMoneyValidation(srcAccount, dstAccount, transferDto.getAmount());

    //송금이벤트를 실행한다.
    MoneyTransferCommand command = MoneyTransferCommand.of(transferDto);
    commandGateway.sendAndWait(command);
    return command.getTransferId();
  }

  private void transferMoneyValidation(AccountQueryDto.Response srcAccount, AccountQueryDto.Response dstAccount, Long amount) {
    //출금계좌가 존재하는지 확인
    if (srcAccount == null) {
      //출금계좌가 존재하지 않는다
      throw new NotFoundAccountException(srcAccount.getAccountId(), NotFoundAccountException.AccountTransferType.WITHDRAW);
    }
    //입금계좌가 존재하는지 확인

    if (dstAccount == null) {
      //입금계좌가 존재하지 않는다
      throw new NotFoundAccountException(dstAccount.getAccountId(), NotFoundAccountException.AccountTransferType.DEPOSIT);
    }

    //출금계좌의 잔고가 있는지 확인
    if (amount > srcAccount.getBalance()) {
      throw new NotEnoughMoneyException(srcAccount.getAccountId(), amount, srcAccount.getBalance());
    }

  }
}
