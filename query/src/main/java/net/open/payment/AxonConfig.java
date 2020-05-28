package net.open.payment;

import net.open.payment.version.HolderCreationEventV1;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BNBAEK
 * Package : net.open.payment
 * User: dean
 * Date: 2020/05/28
 * Time: 2:15 오후
 */
@Configuration
public class AxonConfig {
  @Autowired
  public void configure(EventProcessingConfigurer configurer) {
    configurer.registerTrackingEventProcessor(
        "holderSummaryProjection"
        , org.axonframework.config.Configuration::eventStore
        , c -> TrackingEventProcessorConfiguration.forSingleThreadedProcessing()
            .andBatchSize(100)
    );
    // 순서 보장Sequencing 정책
    configurer.registerSequencingPolicy("holderSummaryProjection",
        configuration -> SequentialPerAggregatePolicy.instance());
  }

  //eventChain
  @Bean
  public EventUpcasterChain eventUpcasterChain() {
    return new EventUpcasterChain(
        new HolderCreationEventV1()
    );
  }
}