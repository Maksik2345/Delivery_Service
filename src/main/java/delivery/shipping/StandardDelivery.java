package delivery.shipping;

import java.math.BigDecimal;
import java.time.Duration;

public class StandardDelivery extends CourierDelivery {

    public StandardDelivery() {
        super("Стандартная доставка", new BigDecimal("200"),
                new BigDecimal("5"), Duration.ofDays(3));
    }
}