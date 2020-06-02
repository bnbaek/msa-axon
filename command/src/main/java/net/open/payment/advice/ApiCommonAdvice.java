package net.open.payment.advice;

import lombok.extern.slf4j.Slf4j;
import net.open.payment.advice.exception.*;
import net.open.payment.api.common.ApiResponseDto;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BNBAEK
 * Package : net.open.payment.advice
 * User: dean
 * Date: 2020/06/02
 * Time: 2:40 오후
 */
@Slf4j
@Order(value = 1)
@RestControllerAdvice(basePackages = "net.open.payment.api")
public class ApiCommonAdvice {

  /**
   * createException
   */
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler
  public ApiResponseDto<String> handleBaseException(ApiException e) {
    return ApiResponseDto.createException(e);
  }


  /**
   *
   */
  //HTTPSTATUS 5xx
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({NotEnoughMoneyException.class})
  public ApiResponseDto<Map<String, String>> handleNotEnoughBaromonyException(NotEnoughMoneyException e) {
    Long possibleAmount = e.getPossibleAmount();
    Long requestAmount = e.getRequestAmount();
    Map<String, String> error = new HashMap<>();
    error.put("possibleAmount", possibleAmount.toString());
    error.put("requestAmount", requestAmount.toString());
    error.put("accountId", e.getAccountId());
    ApiResponseDto<Map<String, String>> exception = ApiResponseDto.createException(ApiResponseCode.NOT_ENOUGH_MONEY, error);
    log.error("[{}] {}", ApiResponseCode.NOT_ENOUGH_MONEY.getId(), exception);
    return exception;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({AlreadyExistDataException.class})
  public ApiResponseDto<Map<String, String>> handleAlreadyExistDataException(AlreadyExistDataException e) {
    String id = e.getId();
    Map<String, String> error = new HashMap<>();
    error.put("id", id);
    return ApiResponseDto.createException(ApiResponseCode.ALREADY_EXIST_DATA, error);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({NotFoundHolderException.class})
  public ApiResponseDto<Map<String, String>> handleNotFoundHolderException(NotFoundHolderException e) {
    Map<String, String> error = new HashMap<>();
    error.put("holderId", e.getHolderId());
    return ApiResponseDto.createException(ApiResponseCode.NOT_FOUND_HOLDER, error);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({NotFoundAccountException.class})
  public ApiResponseDto<Map<String, String>> handleNotFoundAccountException(NotFoundAccountException e) {
    Map<String, String> error = new HashMap<>();
    error.put("accountId", e.getAccountId());
    return ApiResponseDto.createException(ApiResponseCode.NOT_FOUND_ACCOUNT, error);
  }

  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
   * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
   * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ApiResponseDto<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("handleMethodArgumentNotValidException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    return ApiResponseDto.createException(ApiResponseCode.INVALID_INPUT_VALUE, response);
  }

  /**
   * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
   * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  protected ApiResponseDto<ErrorResponse> handleBindException(BindException e) {

    log.error("handleBindException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    return ApiResponseDto.createException(ApiResponseCode.INVALID_INPUT_VALUE, response);
  }

  /**
   * enum type 일치하지 않아 binding 못할 경우 발생
   * 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ApiResponseDto<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    log.error("handleMethodArgumentTypeMismatchException", e);
    final ErrorResponse response = ErrorResponse.of(e);
    return ApiResponseDto.createException(ApiResponseCode.ENUM_TYPE_VALUE, response);
  }

  /**
   * 지원하지 않은 HTTP method 호출 할 경우 발생
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupportedException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }


}