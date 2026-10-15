package delivery.shipping;

import delivery.model.Order;

import java.math.BigDecimal;
import java.time.Duration;

public class PickupDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Самовывоз";
    }

    @Override
    public DeliveryQuote calculateQuote(Order order) {
        return new DeliveryQuote(BigDecimal.ZERO, Duration.ofHours(1));
    }

    @Override
    public boolean requiresCourier() {
        return false;
    }
}