package net.open.payment.repository.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by BNBAEK
 * Package : net.open.payment.repository.account
 * User: dean
 * Date: 2020/06/01
 * Time: 2:26 오후
 */
@Getter
@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

  @Id
  @Column(name = "account_id", nullable = false)
  private String accountId;

  private String holderId;

  private LocalDateTime createdAt;

  @Builder
  public Account(String accountId, String holderId, LocalDateTime createdAt) {
    this.accountId = accountId;
    this.holderId = holderId;
    this.createdAt = createdAt;
  }
}
