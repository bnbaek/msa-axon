package net.open.payment.service.test;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 4:05 오후
 */

/**
 * 추후 사용가능한지 확인
 * eventStore에 쿼리를 해본다.
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
