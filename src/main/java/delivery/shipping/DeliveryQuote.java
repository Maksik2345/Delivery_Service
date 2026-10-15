package delivery.shipping;

import java.math.BigDecimal;
import java.time.Duration;

/** Расчет стоимости доставки*/
public class DeliveryQuote {
    private final BigDecimal cost;
    private final Duration estimatedTime;

    public DeliveryQuote(BigDecimal cost, Duration estimatedTime) {
        this.cost = cost;
        this.estimatedTime = estimatedTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }
}