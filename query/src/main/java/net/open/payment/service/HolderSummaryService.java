package net.open.payment.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 1:53 오후
 */
@Service
@RequiredArgsConstructor
public class HolderSummaryService {
  private final Configuration configuration;

  public void reset() {
    configuration.eventProcessingConfiguration()
        .eventProcessorByProcessingGroup("holderSummaryProjection",
            TrackingEventProcessor.class)
        .ifPresent(trackingEventProcessor -> {
          trackingEventProcessor.shutDown();
          trackingEventProcessor.resetTokens(); // (1)
          trackingEventProcessor.start();
        });
  }
  public void set() {
    configuration.eventProcessingConfiguration()
        .eventProcessorByProcessingGroup("holderSummaryProjection",
            TrackingEventProcessor.class)
        .ifPresent(trackingEventProcessor -> {
          trackingEventProcessor.shutDown();
          trackingEventProcessor.resetTokens(); // (1)
          trackingEventProcessor.start();
        });
  }
}
