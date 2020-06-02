package net.open.payment.service.holder;

import net.open.payment.dto.HolderDto;

import java.util.concurrent.CompletableFuture;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service.holder
 * User: dean
 * Date: 2020/06/02
 * Time: 5:44 오후
 */
public interface HolderService {
  CompletableFuture<String> createHolder(HolderDto.Base holderDto);
}
