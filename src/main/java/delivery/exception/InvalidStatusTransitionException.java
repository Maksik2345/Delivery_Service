package delivery.exception;

import delivery.model.OrderStatus;

public class InvalidStatusTransitionException extends DeliveryException {

    public InvalidStatusTransitionException(OrderStatus from, OrderStatus to) {
        super("Нельзя перевести заказ из статуса «" + from.getTitle()
                + "» в статус «" + to.getTitle() + "»");
    }

    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}