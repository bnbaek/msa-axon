package net.open.payment.advice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements EnumType {
  OK("요청이 성공하였습니다.")
  , BAD_PARAMETER("요청 파라미터가 잘못되었습니다.")
  , NOT_FOUND("리소스를 찾지 못했습니다.")
  , UNAUTHORIZED("인증에 실패하였습니다.")
  , SERVER_ERROR("서버 에러입니다.")
  , NOT_ENOUGH_MONEY("잔고가 부족합니다.")
  , NOT_FOUND_HOLDER("일치하는 계정을(를) 찾을수 없습니다.")
  , NOT_FOUND_ACCOUNT("일치하는 계좌을(를) 찾을수 없습니다.")
  , NOT_FOUND_ORDERNO("일치하는 주문번호를 찾을수 입니다.")
  , DUPLICATE_ORDERNO("이미 사용된 주문번호 입니다.")
  , PAYMENT_WRONG_TOTALAMOUNT("총금액과 items의 합계가 틀립니다.")
  , PAYMENT_WRONG_PARTNER_AMOUNT("정산업체의 금액합계는 0보다 커야합니다.")
  , NOT_FOUND_PAYMENTID("결제번호를 확인해주세요.")
  , NOT_FOUND_ORDER("일치하는 주문을 찾을수 없습니다.")
  , NOT_ENOUGH_REFUND_QUANTITY("취소 가능 수량이 부족합니다.")
  , NOT_FOUND_RESULT("통신 결과 데이터가 없습니다.")
  , RESULT_FAIL("통신 요청에 실패를 응답 받았습니다.")
  , ALREADY_EXIST_DATA("이미 존재하는 데이터 입니다.")
  , NOT_ENOUGH_STATUS("변경 불가능한 상태입니다.")
  ,INVALID_INPUT_VALUE(" Invalid Input Value")
  ,METHOD_NOT_ALLOWED( " Invalid Input Value")
  ,ENTITY_NOT_FOUND( " Entity Not Found")
  ,INTERNAL_SERVER_ERROR( "Server Error")
  ,INVALID_TYPE_VALUE( " Invalid Type Value")
  ,ENUM_TYPE_VALUE( " Invalid enum Type Value")
  ;

  private final String message;

  public String getId() {
    return name();
  }

  @Override
  public String getText() {
    return message;
  }
}