package delivery.shipping;

/**
 * Общая часть способов доставки курьером.
 * Стоимость и срок каждый наследник рассчитывает по-своему.
 */
public abstract class CourierDelivery implements DeliveryMethod {
    private final String name;

    protected CourierDelivery(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}