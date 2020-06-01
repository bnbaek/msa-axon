package net.open.payment.projection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.event.AccountCreationEvent;
import net.open.payment.event.DepositMoneyEvent;
import net.open.payment.event.HolderCreationEvent;
import net.open.payment.event.WithdrawMoneyEvent;
import net.open.payment.repository.account.Account;
import net.open.payment.repository.account.AccountRepository;
import net.open.payment.repository.account.AccountSummary;
import net.open.payment.repository.account.AccountSummaryRepository;
import net.open.payment.repository.holder.Holder;
import net.open.payment.repository.holder.HolderRepository;
import net.open.payment.repository.holder.HolderSummary;
import net.open.payment.repository.holder.HolderSummaryRepository;
import net.open.payment.service.AccountDto;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;

/**
 * Created by BNBAEK
 * Package : net.open.payment.projection
 * User: dean
 * Date: 2020/05/28
 * Time: 11:18 오전
 */
@Component
@AllArgsConstructor
@Slf4j
@ProcessingGroup("holderSummaryProjection")
@EnableRetry
public class HolderSummaryProjection {
  private final AccountRepository accountRepository;
  private final AccountSummaryRepository accountSummaryRepository;
  private final HolderRepository holderRepository;
  private final HolderSummaryRepository holderSummaryRepository;
  private final QueryUpdateEmitter queryUpdateEmitter;

  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(HolderCreationEvent event, @Timestamp Instant instant) {
    log.info("HolderCreationEvent : projecting {} , timestamp : {}", event, instant.toString());

    String holderId = event.getHolderId();
    String holderName = event.getHolderName();
    String address = event.getAddress();
    String userId = event.getUserId();
    String phoneNumber = event.getPhoneNumber();
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));

    //HOLDER 생성
    Holder holder = Holder.builder()
        .holderId(holderId)
        .userId(userId)
        .name(holderName)
        .address(event.getAddress())
        .phoneNumber(phoneNumber)
        .createdAt(dateTime)
        .build();
    holderRepository.save(holder);

    //HOLDER SUMMARY
    HolderSummary build = HolderSummary.builder()
        .holderId(holderId)
        .totalBalance(0L)
        .accountCnt(0L)
        .createdAt(dateTime)
        .updatedAt(dateTime)
        .build();
    holderSummaryRepository.save(build);
  }

  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(AccountCreationEvent event, @Timestamp Instant instant) {
    log.info("AccountCreationEvent: projecting {} , timestamp : {}", event, instant.toString());

    LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
    String accountId = event.getAccountId();
    String holderId = event.getHolderId();

    //HOLDERSUMMARY에 account count add;
    HolderSummary holderSummary = getHolderSummary(holderId);
    holderSummary.setAccountCnt(holderSummary.getAccountCnt() + 1);
    holderSummary.setUpdatedAt(datetime);
    holderSummaryRepository.save(holderSummary);


    //계좌 개설
    accountRepository.save(Account.builder()
        .holderId(holderId)
        .accountId(accountId)
        .createdAt(datetime).build());

    //계좌 summary
    accountSummaryRepository.save(
        AccountSummary.builder()
            .accountId(accountId)
            .totalBalance(0L)
            .createdAt(datetime)
            .build());

  }


  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(WithdrawMoneyEvent event, @Timestamp Instant instant) {
    log.info("WithdrawMoneyEvent: projecting {} , timestamp : {}", event, instant.toString());

    LocalDateTime updatedAt = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
    String holderId = event.getHolderId();
    String accountId = event.getAccountId();
    Long amount = event.getAmount();

    HolderSummary holderSummary = getHolderSummary(holderId);
    AccountSummary accountSummary = getAccountSummary(accountId);

    holderSummary.setTotalBalance(holderSummary.getTotalBalance() - amount);
    holderSummary.setUpdatedAt(updatedAt);

    accountSummary.withdrawMoney(amount, updatedAt);

    queryUpdateEmitter.emit(AccountDto.InfoReq.class
        , query -> query.getHolderId().equals(event.getHolderId())
        , holderSummary);
    holderSummaryRepository.save(holderSummary);


  }


  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(DepositMoneyEvent event, @Timestamp Instant instant) {
    log.info("DepositMoneyEvent: projecting {} , timestamp : {}", event, instant.toString());
    LocalDateTime updatedAt = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
    String holderId = event.getHolderId();
    String accountId = event.getAccountId();

    HolderSummary holderSummary = getHolderSummary(holderId);
    AccountSummary accountSummary = getAccountSummary(accountId);
    Long amount = event.getAmount();

    holderSummary.setTotalBalance(holderSummary.getTotalBalance() + amount);
    holderSummary.setUpdatedAt(updatedAt);

    accountSummary.dipositMoney(amount, updatedAt);

    queryUpdateEmitter.emit(AccountDto.InfoReq.class
        , query -> query.getHolderId().equals(holderId)
        , holderSummary);

    holderSummaryRepository.save(holderSummary);
  }


  private HolderSummary getHolderSummary(String holderId) {
    return holderSummaryRepository.findByHolderId(holderId)
        .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
  }

  private AccountSummary getAccountSummary(String accountId) {
    return accountSummaryRepository.findByAccountId(accountId)
        .orElseThrow(() -> new NoSuchElementException("계좌가 존재하지 않습니다."));
  }


  @ResetHandler
  private void resetHolderAccountInfo() {
    log.debug("reset triggered");
    holderSummaryRepository.deleteAll();
  }

  @QueryHandler
  public HolderSummary on(AccountDto.InfoReq query) {
    log.debug("HolderSummary halding{}", query);
    return holderSummaryRepository.findByHolderId(query.getHolderId()).orElse(null);

  }


}
