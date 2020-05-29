package net.open.payment.projection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.AccountCreationEvent;
import net.open.payment.DepositMoneyEvent;
import net.open.payment.HolderCreationEvent;
import net.open.payment.WithdrawMoneyEvent;
import net.open.payment.accountSummary.HolderSummary;
import net.open.payment.accountSummary.HolderSummaryRepository;
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
  private final HolderSummaryRepository rep;
  private final QueryUpdateEmitter queryUpdateEmitter;

  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(HolderCreationEvent event, @Timestamp Instant instant) {
    log.info("HolderCreationEvent : projecting {} , timestamp : {}", event, instant.toString());
    HolderSummary build = HolderSummary.builder()
        .holderId(event.getHolderId())
        .name(event.getHolderName())
        .address(event.getAddress())
        .userId(event.getUserId())
        .phoneNumber(event.getPhoneNumber())
        .totalBalance(0L)
        .accountCnt(0L)
        .createdAt(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")))
        .updatedAt(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")))
        .build();
    HolderSummary save = rep.save(build);
  }

  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(AccountCreationEvent event, @Timestamp Instant instant) {
    log.info("AccountCreationEvent: projecting {} , timestamp : {}", event, instant.toString());
    HolderSummary holderSummary = getHolderSummary(event.getHolderId());
    holderSummary.setAccountCnt(holderSummary.getAccountCnt() + 1);
    holderSummary.setUpdatedAt(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")));
    rep.save(holderSummary);
  }


  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(WithdrawMoneyEvent event, @Timestamp Instant instant) {
    log.info("WithdrawMoneyEvent: projecting {} , timestamp : {}", event, instant.toString());
    HolderSummary holderSummary = getHolderSummary(event.getHolderId());
    holderSummary.setTotalBalance(holderSummary.getTotalBalance() - event.getAmount());
    holderSummary.setUpdatedAt(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")));
    queryUpdateEmitter.emit(AccountDto.InfoReq.class
        , query -> query.getHolderId().equals(event.getHolderId())
        , holderSummary);
    rep.save(holderSummary);
  }


  @EventHandler
  @Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
  @AllowReplay
  protected void on(DepositMoneyEvent event, @Timestamp Instant instant) {
    log.info("DepositMoneyEvent: projecting {} , timestamp : {}", event, instant.toString());
    HolderSummary holderSummary = getHolderSummary(event.getHolderId());
    holderSummary.setTotalBalance(holderSummary.getTotalBalance() + event.getAmount());
    holderSummary.setUpdatedAt(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")));

    queryUpdateEmitter.emit(AccountDto.InfoReq.class
        , query -> query.getHolderId().equals(event.getHolderId())
        , holderSummary);

    rep.save(holderSummary);
  }


  private HolderSummary getHolderSummary(String holderId) {
    return rep.findByHolderId(holderId)
        .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
  }


  @ResetHandler
  private void resetHolderAccountInfo() {
    log.debug("reset triggered");
    rep.deleteAll();
  }

  @QueryHandler
  public HolderSummary on(AccountDto.InfoReq query) {
    log.debug("HolderSummary halding{}", query);
    return rep.findByHolderId(query.getHolderId()).orElse(null);

  }


}
