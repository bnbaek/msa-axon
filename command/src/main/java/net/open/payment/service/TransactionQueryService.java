package net.open.payment.service;

import java.util.List;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 4:04 오후
 */
public interface TransactionQueryService {

  List<Object> getHolder(String userId);

}
