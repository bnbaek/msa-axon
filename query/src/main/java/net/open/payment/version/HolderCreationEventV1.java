package net.open.payment.version;

import net.open.payment.event.HolderCreationEvent;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

/**
 * Created by BNBAEK
 * Package : net.open.payment.version
 * User: dean
 * Date: 2020/05/28
 * Time: 3:04 오후
 */
public class HolderCreationEventV1 extends SingleEventUpcaster {
  private static SimpleSerializedType targetType = new SimpleSerializedType(HolderCreationEvent.class.getTypeName(), null);

  @Override
  protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
    return intermediateRepresentation.getType().equals(targetType);
  }

  @Override
  protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
    return intermediateRepresentation.upcastPayload(
        new SimpleSerializedType(targetType.getName(), "1.0")
        , org.dom4j.Document.class
        , document -> {
          document.getRootElement()
              .addElement("userId")
              .setText("N/A");
          return document;
        }
    );
  }
}
