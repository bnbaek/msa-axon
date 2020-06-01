package net.open.payment.api;

import lombok.RequiredArgsConstructor;
import net.open.payment.repository.holder.HolderSummary;
import net.open.payment.service.HolderSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;
import reactor.core.publisher.Flux;

/**
 * Created by BNBAEK
 * Package : net.open.payment.api
 * User: dean
 * Date: 2020/05/28
 * Time: 1:52 오후
 */
@RestController
@RequiredArgsConstructor
public class HolderSummaryController {
  private final HolderSummaryService holderSummaryService;

  @PostMapping("/reset")
  public void reset() {
    holderSummaryService.reset();

  }

  @GetMapping("/accounts/{id}")
  public ResponseEntity<HolderSummary> getAccountInfo(@PathVariable(value = "id") @NonNull String holderId) {
    return ResponseEntity.ok(holderSummaryService.getAccountInfo(holderId));
  }

  @GetMapping("/accounts/subscription/{id}")
  public ResponseEntity<Flux<HolderSummary>> getAccountInfoSubscription(@PathVariable(value = "id") @NonNull String holderId) {
    return ResponseEntity.ok(holderSummaryService.getAccountInfoSubsciption(holderId));

  }
}
