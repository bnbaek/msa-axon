package net.open.payment.component;

import lombok.extern.slf4j.Slf4j;
import net.open.payment.query.LoanLimitQuery;
import net.open.payment.query.LoanLimitResult;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

/**
 * Created by BNBAEK
 * Package : net.open.payment.component
 * User: dean
 * Date: 2020/06/01
 * Time: 3:35 오후
 */
@Component
@Slf4j
public class HolderLoanComponent {
  @QueryHandler
  private LoanLimitResult on(LoanLimitQuery query) {
    log.debug("LoanLimitResult handling {}", query);
    return LoanLimitResult.builder()
        .holderId(query.getHolderId())
        .balance(query.getBalance())
        .bankName("appleBank")
        .loanLimit(Double.valueOf(query.getBalance() * 1.2).longValue())
        .build();
  }

}
