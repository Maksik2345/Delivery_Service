package delivery.exception;

public class CourierNotFoundException extends DeliveryException {

    public CourierNotFoundException(long courierId) {
        super("Курьер №" + courierId + " не найден");
    }
}
