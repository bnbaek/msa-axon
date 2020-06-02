package net.open.payment.service.holder;

import lombok.RequiredArgsConstructor;
import net.open.payment.query.HolderQueryDto;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * query 서버에 통신을 하여 데이터를 가지고 온다.
 */

@Service
@RequiredArgsConstructor
public class HolderQueryService {
  private final QueryGateway queryGateway;

  /**
   * query서버에 계정 존재 여부를 확인한다. by holderId
   */
  public List<HolderQueryDto.Response> queryGetHolderById(String holderId) {
    return queryGateway.scatterGather(new HolderQueryDto.HolderIdReq(holderId)
        , ResponseTypes.instanceOf(HolderQueryDto.Response.class)
        , 3, TimeUnit.SECONDS)
        .collect(Collectors.toList());
  }

  /**
   * query서버에 계정 존재 여부를 확인한다. by userId (GROUP:00000 - 추후 별도 페키징)
   */
  public List<HolderQueryDto.Response> queryGetHolderByUserId(String userId) {
    return queryGateway.scatterGather(new HolderQueryDto.UserIdReq(userId)
        , ResponseTypes.instanceOf(HolderQueryDto.Response.class)
        , 3, TimeUnit.SECONDS)
        .collect(Collectors.toList());
  }
}
