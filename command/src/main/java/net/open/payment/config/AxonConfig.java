package net.open.payment.config;

import net.open.payment.aggregate.AccountAggregate;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BNBAEK
 * Package : net.open.payment.config
 * User: dean
 * Date: 2020/05/27
 * Time: 2:36 오후
 */
@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Bean
  public AggregateFactory<AccountAggregate> aggregateFactory() {
    return new GenericAggregateFactory<>(AccountAggregate.class);
  }

  @Bean
  public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager) {
    return AggregateSnapshotter
        .builder()
        .eventStore(eventStore)
        .aggregateFactories(aggregateFactory())
        .transactionManager(transactionManager)
        .build();
  }

  @Bean
  public SnapshotTriggerDefinition snapshotTriggerDefinition(EventStore eventStore, TransactionManager transactionManager) {
    final int SNAPSHOT_TRHRESHOLD = 50;
    return new EventCountSnapshotTriggerDefinition(snapshotter(eventStore, transactionManager), SNAPSHOT_TRHRESHOLD);
  }

  @Bean
  public Cache cache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Repository<AccountAggregate> accountAggregateRepository(EventStore eventStore, SnapshotTriggerDefinition snapshotTriggerDefinition, Cache cache) {
    return CachingEventSourcingRepository
        .builder(AccountAggregate.class)
        .eventStore(eventStore)
        .snapshotTriggerDefinition(snapshotTriggerDefinition)
        .cache(cache)
        .build();
  }
}