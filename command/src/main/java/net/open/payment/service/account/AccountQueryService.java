package net.open.payment.service.account;

import lombok.RequiredArgsConstructor;
import net.open.payment.query.AccountQueryDto;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.account
 * User: dean
 * Date: 2020/06/02
 * Time: 6:12 오후
 */
@Service
@RequiredArgsConstructor
public class AccountQueryService {
  private final QueryGateway queryGateway;

  //계정 정보를 가지고온다.
  public AccountQueryDto.Response queryGetAccount(String accountId) {
    List<AccountQueryDto.Response> responses = queryGateway.scatterGather(new AccountQueryDto.Request(accountId)
        , ResponseTypes.instanceOf(AccountQueryDto.Response.class)
        , 3, TimeUnit.SECONDS)
        .collect(Collectors.toList());

    if (responses.get(0) == null) {
      return null;
    }

    return responses.get(0);

  }
}
