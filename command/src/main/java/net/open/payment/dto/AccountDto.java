package net.open.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by BNBAEK
 * Package : net.open.payment.dto
 * User: dean
 * Date: 2020/05/27
 * Time: 10:56 오전
 */
public class AccountDto {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  public static class Base {
    private String holderId;

  }
}
