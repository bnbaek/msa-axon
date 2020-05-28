package net.open.payment.accountSummary;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by BNBAEK
 * Package : net.open.payment.accountSummary
 * User: dean
 * Date: 2020/05/28
 * Time: 11:06 오전
 */
@Entity
@Table(name = "ACCOUNT_SUMMARY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HolderSummary {
  @Id
  @Column(name = "holder_id", nullable = false)
  private String holderId;

  @Column(name = "user_id")
  private String userId;

  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String phoneNumber;
  @Column
  private String address;
  @Column(name = "total_balance", nullable = false)
  private Long totalBalance;
  @Column(name = "account_cnt", nullable = false)
  private Long accountCnt;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
