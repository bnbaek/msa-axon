package net.open.payment.service.holder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.advice.exception.AlreadyExistDataException;
import net.open.payment.commands.HolderCreationCommand;
import net.open.payment.dto.HolderDto;
import net.open.payment.query.HolderQueryDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.holder
 * User: dean
 * Date: 2020/06/02
 * Time: 5:45 오후
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HolderServiceImpl implements HolderService {
  private final CommandGateway commandGateway;
  private final HolderQueryService holderQueryService;

  /**
   * 거래 계정을 생성한다.
   */
  @Override
  public CompletableFuture<String> createHolder(HolderDto.Base holderDto) {
    String holderId = UUID.randomUUID().toString();
    String userId = holderDto.getUserId().trim();

    //validation
    List<HolderQueryDto.Response> holders = holderQueryService.queryGetHolderByUserId(userId);

    if (holders.size() > 0) {
      throw new AlreadyExistDataException("이미 존재하는 계정ID 입니다.", userId);
    }
    //gateway에 계좌생성 이벤트를 보낸다.
    commandGateway.send(HolderCreationCommand.builder()
        .holderId(holderId)
        .holderName(holderDto.getHolderName())
        .address(holderDto.getAddress())
        .phoneNumber(holderDto.getPhoneNumber())
        .userId(holderDto.getUserId())
        .build());
    return CompletableFuture.supplyAsync(() -> holderId);
  }


}
