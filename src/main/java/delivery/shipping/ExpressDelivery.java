package delivery.shipping;

import java.math.BigDecimal;
import java.time.Duration;

public class ExpressDelivery extends CourierDelivery {

    public ExpressDelivery() {
        super("Экспресс-доставка", new BigDecimal("500"),
                new BigDecimal("10"), Duration.ofHours(3));
    }
}