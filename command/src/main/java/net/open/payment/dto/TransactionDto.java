package net.open.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

  @NotBlank(message = "accountId 는 필수값 입니다.")
  private String accountId;
  @NotBlank(message = "accountId 는 필수값 입니다.")
  private String holderId;
  @Min(value = 1, message = "금액은 0보다 커야한다.")
  private Long amount;

  @Builder
  public TransactionDto(String accountId, String holderId, Long amount) {
    this.accountId = accountId;
    this.holderId = holderId;
    this.amount = amount;
  }
}
