package net.open.payment.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/29
 * Time: 11:04 오전
 */


public class AccountDto {

  @Getter
  @ToString
  public static class InfoReq{

    private String holderId;

    @Builder
    public InfoReq(String holderId) {
      this.holderId = holderId;
    }
  }


}
