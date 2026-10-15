package delivery.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long orderId) {
        super("Заказ №" + orderId + " не найден");
    }
}
