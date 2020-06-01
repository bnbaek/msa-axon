package net.open.payment.service.loan;

import net.open.payment.query.LoanLimitResult;

import java.util.List;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.loan
 * User: dean
 * Date: 2020/06/01
 * Time: 4:09 오후
 */
public interface LoanService {
  List<LoanLimitResult> getHolderScatterGather(String holdId);
}
