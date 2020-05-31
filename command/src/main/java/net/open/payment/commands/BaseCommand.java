package net.open.payment.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Created by iopenu@gmail.com on 2020/05/31
 * Github : https://github.com/bnbaek
 */
@Getter
public class BaseCommand<T> {
  @TargetAggregateIdentifier
  public final T id;

  public BaseCommand(T id) {
    this.id = id;
  }
}
