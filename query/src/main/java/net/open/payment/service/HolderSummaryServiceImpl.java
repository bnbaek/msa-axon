package net.open.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.repository.holder.HolderSummary;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/29
 * Time: 11:43 오전
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HolderSummaryServiceImpl implements HolderSummaryService {
  private final Configuration configuration;
  private final QueryGateway queryGateway;

  @Override
  public void reset() {
    configuration.eventProcessingConfiguration()
        .eventProcessorByProcessingGroup("holderSummaryProjection"
            , TrackingEventProcessor.class)
        .ifPresent(trackingEventProcessor -> {
          trackingEventProcessor.shutDown();
          trackingEventProcessor.resetTokens(); // (1)
          trackingEventProcessor.start();
        });
  }

  @Override
  public HolderSummary getAccountInfo(String holderId) {
    AccountDto.InfoReq req = AccountDto.InfoReq.builder().holderId(holderId).build();
    log.debug("[getAccountInfo]handing {}", req);
    return queryGateway.query(req, ResponseTypes.instanceOf(HolderSummary.class)).join();
  }

  @Override
  public Flux<HolderSummary> getAccountInfoSubsciption(String holderId) {
    AccountDto.InfoReq req = AccountDto.InfoReq.builder().holderId(holderId).build();
    log.debug("[getAccountInfo]handing {}", req);
    SubscriptionQueryResult<HolderSummary, HolderSummary> queryResult = queryGateway.subscriptionQuery(req, ResponseTypes.instanceOf(HolderSummary.class)
        , ResponseTypes.instanceOf(HolderSummary.class));
    return Flux.create(emitter -> {
      queryResult.initialResult().subscribe(emitter::next);
      queryResult.updates()
          .doOnNext(holder -> {
            log.debug("doOnNext : {}, isCanceled {}",holder,emitter.isCancelled());
            if(emitter.isCancelled()){
              queryResult.close();
            }
          })
          .doOnComplete(emitter::complete)
          .subscribe(emitter::next);

    });

  }
}
