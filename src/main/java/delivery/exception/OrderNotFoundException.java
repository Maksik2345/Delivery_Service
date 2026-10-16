package delivery.exception;

public class OrderNotFoundException extends DeliveryException {

    public OrderNotFoundException(long orderId) {
        super("Заказ №" + orderId + " не найден");
    }
}
