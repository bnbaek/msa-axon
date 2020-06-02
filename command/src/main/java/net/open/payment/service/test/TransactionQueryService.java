package net.open.payment.service.test;

import java.util.List;

/**
 * Created by BNBAEK
 * Package : net.open.payment.service
 * User: dean
 * Date: 2020/05/28
 * Time: 4:04 오후
 */

/**
 * 테스트한것 비지니스 로직과 무관.
 */
public interface TransactionQueryService {

  List<Object> getHolder(String userId);

}
