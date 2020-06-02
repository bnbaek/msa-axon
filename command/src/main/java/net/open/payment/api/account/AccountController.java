package net.open.payment.api.account;

import lombok.RequiredArgsConstructor;
import net.open.payment.dto.AccountDto;
import net.open.payment.service.account.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * 계좌를 관리한다.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/accounts", "/api/v1/accounts"})
public class AccountController {
  private final AccountService accountService;

  /**
   * 계좌를 생성한다.
   */
  @PostMapping
  public CompletableFuture<String> createAccount(@Valid @RequestBody AccountDto.Base request) {
    return accountService.createAccount(request);
  }

}
