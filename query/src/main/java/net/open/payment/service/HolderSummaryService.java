package net.open.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.accountSummary.HolderSummary;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 1:53 오후
 */
public interface HolderSummaryService {
  void reset();

  HolderSummary getAccountInfo(String holderId);

  Flux<HolderSummary> getAccountInfoSubsciption(String holderId);
}