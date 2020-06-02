package net.open.payment.api.holder;

import lombok.RequiredArgsConstructor;
import net.open.payment.dto.HolderDto;
import net.open.payment.service.holder.HolderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * 계정을 관리한다.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/holders", "/api/v1/holders"})
public class HolderController {

  private final HolderService holderService;

  //계정을 생성하는 api
  @PostMapping
  public CompletableFuture<String> createHolder(@RequestBody HolderDto.Base request) throws NoSuchFieldException {
    return holderService.createHolder(request);
  }
}
