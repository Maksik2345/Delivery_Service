package delivery.ui;

import delivery.model.Courier;
import delivery.model.Order;
import delivery.model.OrderItem;
import delivery.shipping.DeliveryMethod;
import delivery.shipping.DeliveryQuote;

import java.time.Duration;
import java.util.List;

public class OrderPrinter {

    public void printOrder(Order order) {
        System.out.println("-----------------------------------");
        System.out.println("Заказ №" + order.getId() + " | Статус: " + order.getStatus().getTitle());
        System.out.println("Клиент: " + order.getCustomer().getName()
                + ", тел. " + order.getCustomer().getPhone());
        System.out.println("Адрес: " + order.getAddress());
        System.out.println("Позиции:");
        if (order.getItems().isEmpty()) {
            System.out.println("  (нет)");
        }
        for (OrderItem item : order.getItems()) {
            System.out.println("  " + item.getProductName() + " x" + item.getQuantity()
                    + " по " + item.getUnitPrice() + " = " + item.getTotalPrice());
        }
        if (order.getDeliveryMethod() == null) {
            System.out.println("Доставка: не выбрана");
        } else {
            DeliveryQuote quote = order.getDeliveryQuote();
            System.out.println("Доставка: " + order.getDeliveryMethod().getName()
                    + ", " + quote.getCost() + " руб., срок " + formatDuration(quote.getEstimatedTime()));
        }
        Courier courier = order.getCourier();
        System.out.println("Курьер: " + (courier == null ? "не назначен" : courier.getName()));
        System.out.println("Итого: " + order.getTotalCost() + " руб.");
        System.out.println("-----------------------------------");
    }

    public void printOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        orders.forEach(this::printOrder);
    }

    public void printQuote(DeliveryQuote quote) {
        System.out.println("Стоимость доставки: " + quote.getCost() + " руб.");
        System.out.println("Ориентировочный срок: " + formatDuration(quote.getEstimatedTime()));
    }

    public void printMethods(List<DeliveryMethod> methods) {
        for (int i = 0; i < methods.size(); i++) {
            System.out.println((i + 1) + ". " + methods.get(i).getName());
        }
    }

    public void printCouriers(List<Courier> couriers) {
        for (Courier courier : couriers) {
            System.out.println(courier.getId() + ". " + courier.getName()
                    + " (" + (courier.isAvailable() ? "свободен" : "занят") + ")");
        }
    }

    private String formatDuration(Duration duration) {
        if (duration.toHours() < 24) {
            return Math.max(duration.toHours(), 1) + " ч";
        }
        return duration.toDays() + " дн.";
    }
}