package net.open.payment.repository.account;

import net.open.payment.repository.holder.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by BNBAEK
 * Package : net.open.payment.repository.account
 * User: dean
 * Date: 2020/06/01
 * Time: 2:29 오후
 */
public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findByAccountId(String accountId);
}
