package net.open.payment.service.holder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.query.AccountQueryDto;
import net.open.payment.repository.account.Account;
import net.open.payment.repository.account.AccountRepository;
import net.open.payment.repository.account.AccountSummary;
import net.open.payment.repository.account.AccountSummaryRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.holder
 * User: dean
 * Date: 2020/06/02
 * Time: 6:21 오후
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AccountComponent {
  private final AccountRepository accountRepository;
  private final AccountSummaryRepository accountSummaryRepository;

  @QueryHandler
  private AccountQueryDto.Response on(AccountQueryDto.Request query) {
    log.debug("accountQuery.query {}");

    String accountId = query.getAccountId();
    Account account = accountRepository.findByAccountId(accountId).orElse(null);
    AccountSummary accountSummary = accountSummaryRepository.findByAccountId(accountId).orElse(null);
    if (account == null) {
      return null;
    }

    return AccountQueryDto.Response.builder()
        .accountId(account.getAccountId())
        .holderId(account.getHolderId())
        .balance(accountSummary.getTotalBalance())
        .build();

  }
}
