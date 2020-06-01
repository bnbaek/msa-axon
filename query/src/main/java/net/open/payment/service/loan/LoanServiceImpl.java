package net.open.payment.service.loan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.open.payment.query.LoanLimitQuery;
import net.open.payment.query.LoanLimitResult;
import net.open.payment.repository.holder.Holder;
import net.open.payment.repository.holder.HolderSummary;
import net.open.payment.repository.holder.HolderSummaryRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.loan
 * User: dean
 * Date: 2020/06/01
 * Time: 4:10 오후
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

  private final QueryGateway queryGateway;

  private final HolderSummaryRepository holderSummaryRepository;

  @Override
  public List<LoanLimitResult> getHolderScatterGather(String holdId) {
    HolderSummary holderSummary = holderSummaryRepository.findByHolderId(holdId).orElseThrow();

    return queryGateway.scatterGather(new LoanLimitQuery(holderSummary.getHolderId(), holderSummary.getTotalBalance())
        , ResponseTypes.instanceOf(LoanLimitResult.class)
        , 30, TimeUnit.SECONDS)
        .collect(Collectors.toList());

  }
}
