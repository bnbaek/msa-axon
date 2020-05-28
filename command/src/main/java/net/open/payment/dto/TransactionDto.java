package net.open.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by BNBAEK
 * Package : net.open.payment.dto
 * User: dean
 * Date: 2020/05/27
 * Time: 10:57 오전
 */
@NoArgsConstructor
@Getter
public class TransactionDto {

  private String accountId;
  private String holderId;
  private Long amount;

  @Builder
  public TransactionDto(String accountId, String holderId, Long amount) {
    this.accountId = accountId;
    this.holderId = holderId;
    this.amount = amount;
  }
}
