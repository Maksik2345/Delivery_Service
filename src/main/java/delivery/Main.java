package delivery;

import delivery.model.Courier;
import delivery.repository.CourierRepository;
import delivery.repository.InMemoryCourierRepository;
import delivery.repository.InMemoryOrderRepository;
import delivery.repository.OrderRepository;
import delivery.service.OrderService;
import delivery.shipping.DeliveryMethod;
import delivery.shipping.ExpressDelivery;
import delivery.shipping.PickupDelivery;
import delivery.shipping.StandardDelivery;
import delivery.ui.ConsoleMenu;
import delivery.ui.InputReader;
import delivery.ui.OrderPrinter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        OrderRepository orders = new InMemoryOrderRepository();
        CourierRepository couriers = new InMemoryCourierRepository();
        couriers.save(new Courier("Иван Петров", "+7-900-111-11-11"));
        couriers.save(new Courier("Анна Смирнова", "+7-900-222-22-22"));
        couriers.save(new Courier("Олег Волков", "+7-900-333-33-33"));

        List<DeliveryMethod> methods = List.of(
                new StandardDelivery(), new ExpressDelivery(), new PickupDelivery());

        OrderService service = new OrderService(orders, couriers);
        new ConsoleMenu(service, new InputReader(), new OrderPrinter(), methods).run();
    }
}