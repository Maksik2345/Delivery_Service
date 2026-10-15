package delivery.shipping;

import delivery.model.Order;

public interface DeliveryMethod {

    String getName();

    DeliveryQuote calculateQuote(Order order);

    boolean requiresCourier();
}