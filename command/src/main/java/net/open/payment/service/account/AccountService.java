package net.open.payment.service.account;

import net.open.payment.dto.AccountDto;

import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.account
 * User: dean
 * Date: 2020/06/02
 * Time: 5:56 오후
 */
public interface AccountService {
  CompletableFuture<String> createAccount(AccountDto.Base accountDto);
}
