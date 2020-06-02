package net.open.payment.repository.holder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by BNBAEK
 * Package : net.open.payment.repository.holder
 * User: dean
 * Date: 2020/06/01
 * Time: 2:10 오후
 */
public interface HolderRepository extends JpaRepository<Holder, String> {
  Optional<Holder> findByUserId(String userId);

  Optional<Holder> findByHolderId(String holderId);
}
