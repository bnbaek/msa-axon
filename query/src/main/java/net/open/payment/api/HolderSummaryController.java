package net.open.payment.api;

import lombok.RequiredArgsConstructor;
import net.open.payment.service.HolderSummaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BNBAEK
 * Package : net.open.payment.api
 * User: dean
 * Date: 2020/05/28
 * Time: 1:52 오후
 */
@RestController
@RequiredArgsConstructor
public class HolderSummaryController {
  private final HolderSummaryService holderSummaryService;

  @PostMapping("/reset")
  public void reset(){
    holderSummaryService.reset();

  }
}
