package net.open.payment.api;

import lombok.RequiredArgsConstructor;
import net.open.payment.aggregate.TransferDto;
import net.open.payment.service.transfer.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BNBAEK
 * Package : net.open.payment.api
 * User: dean
 * Date: 2020/06/02
 * Time: 10:21 오전
 */
@RestController
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;

  @PostMapping("/transfer")
  public ResponseEntity<String> transfer(@RequestBody TransferDto transferDto) {
    return ResponseEntity.ok().body(transferService.transferMoney(transferDto));
  }
}