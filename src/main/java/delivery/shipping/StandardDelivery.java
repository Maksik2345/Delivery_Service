package delivery.shipping;

import delivery.model.Order;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Стандартная доставка курьером: фиксированная цена,
 * бесплатно при сумме позиций от порога.
 */
public class StandardDelivery extends CourierDelivery {
    private static final BigDecimal COST = new BigDecimal("200.00");
    private static final BigDecimal FREE_FROM = new BigDecimal("3000");
    private static final Duration ESTIMATED_TIME = Duration.ofDays(3);

    public StandardDelivery() {
        super("Стандартная доставка");
    }

    @Override
    public DeliveryQuote calculateQuote(Order order) {
        boolean isFree = order.getItemsTotal().compareTo(FREE_FROM) >= 0;
        return new DeliveryQuote(isFree ? BigDecimal.ZERO : COST, ESTIMATED_TIME);
    }
}