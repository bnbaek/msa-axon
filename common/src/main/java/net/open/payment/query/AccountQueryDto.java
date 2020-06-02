package net.open.payment.query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.query
 * User: dean
 * Date: 2020/06/02
 * Time: 6:13 오후
 */
public class AccountQueryDto {
  @Getter
  @ToString
  public static class Request {
    private String accountId;

    public Request(String accountId) {
      this.accountId = accountId;
    }
  }

  @Getter
  @ToString
  public static class Response {
    private String holderId;
    private String accountId;
    private Long balance;

    @Builder
    public Response(String holderId, String accountId, Long balance) {
      this.holderId = holderId;
      this.accountId = accountId;
      this.balance = balance;
    }
  }
}
