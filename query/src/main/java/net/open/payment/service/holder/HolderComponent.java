package net.open.payment.service.holder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.query.HolderQueryDto;
import net.open.payment.repository.holder.Holder;
import net.open.payment.repository.holder.HolderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.holder
 * User: dean
 * Date: 2020/06/02
 * Time: 3:43 오후
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HolderComponent {
  private final HolderRepository holderRepository;

  @QueryHandler
  private HolderQueryDto.Response on(HolderQueryDto.UserIdReq query) {
    log.debug("HolderQuery.query {}", query);

    Holder holder = holderRepository.findByUserId(query.getUserId()).orElse(null);
    if (holder == null) {
      return null;
    }
    return new HolderQueryDto.Response(holder.getUserId(), holder.getHolderId());
  }

  @QueryHandler
  private HolderQueryDto.Response on(HolderQueryDto.HolderIdReq query) {
    log.debug("HolderQuery.Response {}", query);

    Holder holder = holderRepository.findByUserId(query.getHolderId()).orElse(null);
    if (holder == null) {
      return null;
    }
    return new HolderQueryDto.Response(holder.getUserId(), holder.getHolderId());
  }

}
