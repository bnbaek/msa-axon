package net.open.payment.service;

import net.open.payment.repository.holder.HolderSummary;
import reactor.core.publisher.Flux;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 1:53 오후
 */
public interface HolderSummaryService {
  void reset();

  HolderSummary getAccountInfo(String holderId);

  Flux<HolderSummary> getAccountInfoSubsciption(String holderId);
}