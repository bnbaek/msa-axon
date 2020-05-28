package net.open.payment.service;

import lombok.RequiredArgsConstructor;
import net.open.payment.aggregate.AccountAggregate;
import net.open.payment.aggregate.HolderAggregate;
import net.open.payment.dto.HolderDto;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 4:05 오후
 */

@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements TransactionQueryService {
  private final EventStore eventStore;
  private final ModelMapper modelMapper;

  @Override
  public List<Object> getHolder(String userId) {
    return eventStore.readEvents(userId).asStream().map(s -> s.getPayload())
        .collect(Collectors.toList());
//    return eventStore.readEvents(accountNumber).asStream().map( s -> s.getPayload()).collect(Collectors.toList());
  }

}
