package delivery.model;

import delivery.exception.CourierAssignmentException;
import delivery.exception.InvalidStatusTransitionException;
import delivery.shipping.DeliveryMethod;
import delivery.shipping.DeliveryQuote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Order {
    private static long nextId = 1;
    private final long id;
    private final Customer customer;
    private final String address;
    private final List<OrderItem> items = new ArrayList<>();
    private DeliveryMethod deliveryMethod;
    private Courier courier;
    private OrderStatus status = OrderStatus.CREATED;

    public Order(Customer customer, String address) {
        if (customer == null) {
            throw new IllegalArgumentException("У заказа должен быть клиент");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Адрес доставки не может быть пустым");
        }
        this.id = nextId++;
        this.customer = customer;
        this.address = address.trim();
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Позиция не может быть пустой");
        }
        if (status != OrderStatus.CREATED) {
            throw new InvalidStatusTransitionException(
                    "Добавлять позиции можно только в новый заказ");
        }
        items.add(item);
    }

    public void chooseDeliveryMethod(DeliveryMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("Способ доставки не выбран");
        }
        if (status != OrderStatus.CREATED) {
            throw new InvalidStatusTransitionException(
                    "Менять способ доставки можно только в новом заказе");
        }
        if (courier != null) {
            throw new CourierAssignmentException(
                    "Курьер уже назначен, сменить способ доставки нельзя");
        }
        this.deliveryMethod = method;
    }

    public void assignCourier(Courier newCourier) {
        if (newCourier == null) {
            throw new IllegalArgumentException("Курьер не выбран");
        }
        if (deliveryMethod == null) {
            throw new CourierAssignmentException("Сначала выберите способ доставки");
        }
        if (!deliveryMethod.requiresCourier()) {
            throw new CourierAssignmentException(
                    "Способ «" + deliveryMethod.getName() + "» не требует курьера");
        }
        if (courier != null) {
            throw new CourierAssignmentException("Курьер уже назначен");
        }
        if (status != OrderStatus.CREATED && status != OrderStatus.CONFIRMED) {
            throw new CourierAssignmentException(
                    "В статусе «" + status.getTitle() + "» курьера назначить нельзя");
        }
        newCourier.assign();
        this.courier = newCourier;
    }

    public void changeStatus(OrderStatus next) {
        if (next == null) {
            throw new IllegalArgumentException("Новый статус не указан");
        }
        if (!status.canTransitionTo(next)) {
            throw new InvalidStatusTransitionException(status, next);
        }
        if (next == OrderStatus.CONFIRMED) {
            if (items.isEmpty()) {
                throw new InvalidStatusTransitionException(
                        "Нельзя подтвердить пустой заказ: добавьте позиции");
            }
            if (deliveryMethod == null) {
                throw new InvalidStatusTransitionException(
                        "Нельзя подтвердить заказ без способа доставки");
            }
        }
        if (next == OrderStatus.IN_DELIVERY
                && deliveryMethod.requiresCourier() && courier == null) {
            throw new CourierAssignmentException("Сначала назначьте курьера");
        }
        status = next;
        if ((next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED) && courier != null) {
            courier.release();
        }
    }

    // сумма заказа без учета доставки
    public BigDecimal getItemsTotal() {
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public DeliveryQuote getDeliveryQuote() {
        if (deliveryMethod == null) {
            throw new IllegalStateException("Способ доставки не выбран");
        }
        return deliveryMethod.calculateQuote(this);
    }

    // итоговая стоимость заказа + способ доставки
    public BigDecimal getTotalCost() {
        BigDecimal delivery = deliveryMethod == null
                ? BigDecimal.ZERO
                : getDeliveryQuote().getCost();
        return getItemsTotal().add(delivery);
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public Courier getCourier() {
        return courier;
    }

    public OrderStatus getStatus() {
        return status;
    }
}