package net.open.payment.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.query.LoanLimitResult;
import net.open.payment.service.loan.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by BNBAEK
 * Package : net.open.payment.api
 * User: dean
 * Date: 2020/06/01
 * Time: 4:19 오후
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoanController {
  private final LoanService loanService;

  @GetMapping("/holders/loan/{id}")
  public ResponseEntity<List<LoanLimitResult>> getHolderLoan(@PathVariable(value = "id") @NonNull String id) {

    return ResponseEntity.ok().body(loanService.getHolderScatterGather(id));
  }
  @GetMapping("/holders/{id}")
  public ResponseEntity<String> gettest(@PathVariable(value = "id") String id){
    return ResponseEntity.ok(id);
  }
}
