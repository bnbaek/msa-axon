package net.open.payment.accountSummary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by BNBAEK
 * Package : net.open.payment.accountSummary
 * User: dean
 * Date: 2020/05/28
 * Time: 11:15 오전
 */
public interface HolderSummaryRepository extends JpaRepository<HolderSummary, String> {
  Optional<HolderSummary> findByHolderId(String holderId);
}
