package net.open.payment.repository.account;

import lombok.AccessLevel;
import lombok.Builder;
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
 * Time: 2:29 오후
 */

@Entity
@Table(name = "ACCOUNT_SUMMARY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSummary {
  @Id
  @Column(name = "account_id", updatable = false)
  private String accountId;

  @Column(name = "total_balance", nullable = false)
  private Long totalBalance;

  @Column(name = "tx_count")
  private Integer txCount;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  public AccountSummary(String accountId, Long totalBalance, LocalDateTime createdAt) {
    this.accountId = accountId;
    this.totalBalance = totalBalance;
    this.txCount = 0;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  public Long dipositMoney(Long amount, LocalDateTime updatedAt) {
    this.totalBalance += amount;
    this.updatedAt = updatedAt;
    this.txCount += 1;
    return this.totalBalance;
  }

  public Long withdrawMoney(Long amount, LocalDateTime updatedAt) {
    this.totalBalance -= amount;
    this.updatedAt = updatedAt;
    this.txCount += 1;
    return this.totalBalance;
  }

}
