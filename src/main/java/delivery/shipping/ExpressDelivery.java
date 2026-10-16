package delivery.shipping;

import delivery.model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

/**
 * Экспресс-доставка курьером: базовая цена + процент от суммы позиций
 */
public class ExpressDelivery extends CourierDelivery {
    private static final BigDecimal BASE_COST = new BigDecimal("500");
    private static final BigDecimal PERCENT = new BigDecimal("10");
    private static final Duration ESTIMATED_TIME = Duration.ofHours(3);

    public ExpressDelivery() {
        super("Экспресс-доставка");
    }

    @Override
    public DeliveryQuote calculateQuote(Order order) {
        BigDecimal surcharge = order.getItemsTotal()
                .multiply(PERCENT)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return new DeliveryQuote(BASE_COST.add(surcharge), ESTIMATED_TIME);
    }
}