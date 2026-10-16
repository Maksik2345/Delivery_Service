package delivery.service;

import delivery.exception.CourierNotFoundException;
import delivery.exception.OrderNotFoundException;
import delivery.model.Courier;
import delivery.model.Customer;
import delivery.model.Order;
import delivery.model.OrderItem;
import delivery.model.OrderStatus;
import delivery.repository.CourierRepository;
import delivery.repository.OrderRepository;
import delivery.shipping.DeliveryMethod;
import delivery.shipping.DeliveryQuote;

import java.math.BigDecimal;
import java.util.List;


public class OrderService {
    private final OrderRepository orders;
    private final CourierRepository couriers;

    public OrderService(OrderRepository orders, CourierRepository couriers) {
        this.orders = orders;
        this.couriers = couriers;
    }

    public Order createOrder(String customerName, String phone, String address) {
        Customer customer = new Customer(customerName, phone);
        Order order = new Order(customer, address);
        orders.save(order);
        return order;
    }

    public void addItem(long orderId, String productName, int quantity, BigDecimal unitPrice) {
        getOrder(orderId).addItem(new OrderItem(productName, quantity, unitPrice));
    }

    public DeliveryQuote chooseDelivery(long orderId, DeliveryMethod method) {
        Order order = getOrder(orderId);
        order.chooseDeliveryMethod(method);
        return order.getDeliveryQuote();
    }

    public void assignCourier(long orderId, long courierId) {
        Order order = getOrder(orderId);
        Courier courier = couriers.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException(courierId));
        order.assignCourier(courier);
    }

    public void changeStatus(long orderId, OrderStatus next) {
        getOrder(orderId).changeStatus(next);
    }

    public Order getOrder(long orderId) {
        return orders.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public List<Order> getAllOrders() {
        return orders.findAll();
    }

    public List<Courier> getCouriers() {
        return couriers.findAll();
    }
}