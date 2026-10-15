package delivery.shipping;

import delivery.model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

/**
 * Общая часть способов доставки курьером:
 * стоимость = базовая цена + процент от суммы позиций
 */
public abstract class CourierDelivery implements DeliveryMethod {
    private final String name;
    private final BigDecimal baseCost;
    private final BigDecimal percentOfItems;
    private final Duration estimatedTime;

    protected CourierDelivery(String name, BigDecimal baseCost,
                              BigDecimal percentOfItems, Duration estimatedTime) {
        this.name = name;
        this.baseCost = baseCost;
        this.percentOfItems = percentOfItems;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DeliveryQuote calculateQuote(Order order) {
        BigDecimal extra = order.getItemsTotal()
                .multiply(percentOfItems)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return new DeliveryQuote(baseCost.add(extra), estimatedTime);
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}