package net.open.payment.query;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by BNBAEK
 * Package : net.open.payment.query
 * User: dean
 * Date: 2020/06/02
 * Time: 3:08 오후
 */
public class HolderQueryDto {
  @Getter
  @ToString
  public static class UserIdReq {
    private String userId;

    public UserIdReq(String userId) {
      this.userId = userId;
    }
  }

  @Getter
  @ToString
  public static class HolderIdReq {
    private String holderId;

    public HolderIdReq(String userId) {
      this.holderId = holderId;
    }
  }


  @Getter
  @ToString
  public static class Response {
    private String userId;
    private String holderId;

    public Response(String userId, String holderId) {
      this.userId = userId;
      this.holderId = holderId;
    }
  }

}
