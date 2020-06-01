package net.open.payment.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by BNBAEK
 * Package : net.open.payment.repository.account
 * User: dean
 * Date: 2020/06/01
 * Time: 2:34 오후
 */
public interface AccountSummaryRepository extends JpaRepository<AccountSummary, String> {
  Optional<AccountSummary> findByAccountId(String accountId);
}
