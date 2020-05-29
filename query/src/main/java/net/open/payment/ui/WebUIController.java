package net.open.payment.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by BNBAEK
 * Package : net.open.payment.ui
 * User: dean
 * Date: 2020/05/29
 * Time: 10:55 오전
 */
@Controller
public class WebUIController {
  @GetMapping("/ui/accounts")
  public void pointToPointQueryView() {
  }

  @GetMapping("/ui/accounts/subscription")
  public void subscriptionQueryView() {
  }

}


