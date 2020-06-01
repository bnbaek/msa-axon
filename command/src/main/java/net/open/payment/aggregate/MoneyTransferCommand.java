package net.open.payment.aggregate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.open.payment.commands.MasterTransferCommand;
import net.open.payment.transfer.factory.TransferComamndFactory;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;
import java.util.function.Function;

/**
 * Created by BNBAEK
 * Package : net.open.payment.aggregate
 * User: dean
 * Date: 2020/06/01
 * Time: 6:29 오후
 */
@Builder
@ToString
@Getter
public class MoneyTransferCommand {
  private String srcAccountId;
  @TargetAggregateIdentifier
  private String dstAccountId;
  private Long amount;
  private String transferId;
  private BankType bankType;

  public enum BankType {
    BAROGO(command -> new TransferComamndFactory(new MasterTransferCommand()));

    private Function<MoneyTransferCommand, TransferComamndFactory> expression;

    BankType(Function<MoneyTransferCommand, TransferComamndFactory> expression) {
      this.expression = expression;
    }

    public TransferComamndFactory getCommandFactory(MoneyTransferCommand command) {
      TransferComamndFactory factory = this.expression.apply(command);
      factory.create(command.getSrcAccountId(), command.getDstAccountId(), command.amount, command.getTransferId());
      return factory;
    }

  }

  public static MoneyTransferCommand of(TransferDto dto) {
    return MoneyTransferCommand.builder()
        .srcAccountId(dto.getSrcAccountId())
        .dstAccountId(dto.getDstAccountId())
        .amount(dto.getAmount())
        .bankType(dto.getBankType())
        .transferId(UUID.randomUUID().toString())
        .build();
  }
}
