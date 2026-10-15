package delivery.ui;

import delivery.exception.DeliveryException;
import delivery.model.Order;
import delivery.model.OrderStatus;
import delivery.service.OrderService;
import delivery.shipping.DeliveryMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class ConsoleMenu {
    private final OrderService service;
    private final InputReader input;
    private final OrderPrinter printer;
    private final List<DeliveryMethod> methods;

    public ConsoleMenu(OrderService service, InputReader input,
                       OrderPrinter printer, List<DeliveryMethod> methods) {
        this.service = service;
        this.input = input;
        this.printer = printer;
        this.methods = methods;
    }

    public void run() {
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = input.readInt("Выберите пункт", 0, 5);
                try {
                    switch (choice) {
                        case 1 -> createOrder();
                        case 2 -> printer.printOrders(service.getAllOrders());
                        case 3 -> chooseDelivery();
                        case 4 -> assignCourier();
                        case 5 -> changeStatus();
                        case 0 -> running = false;
                        default -> System.out.println("Неизвестный пункт меню.");
                    }
                } catch (DeliveryException | IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
            System.out.println("Работа завершена.");
        } catch (NoSuchElementException e) {
            System.out.println("\nВвод завершён. Работа завершена.");
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Служба доставки ===");
        System.out.println("1. Создать заказ");
        System.out.println("2. Показать заказы");
        System.out.println("3. Выбрать способ доставки");
        System.out.println("4. Назначить курьера");
        System.out.println("5. Изменить статус заказа");
        System.out.println("0. Выход");
    }

    private void createOrder() {
        String name = input.readNonBlank("Имя клиента");
        String phone = input.readNonBlank("Телефон");
        String address = input.readNonBlank("Адрес доставки");
        Order order = service.createOrder(name, phone, address);
        System.out.println("Создан заказ №" + order.getId());

        do {
            String product = input.readNonBlank("Название товара");
            int quantity = input.readInt("Количество", 1, 1000);
            BigDecimal price = input.readPrice("Цена за единицу");
            service.addItem(order.getId(), product, quantity, price);
            System.out.println("Позиция добавлена.");
        } while (input.readYesNo("Добавить ещё одну позицию?"));

        printer.printOrder(order);
    }

    private void chooseDelivery() {
        long orderId = readOrderId();
        printer.printMethods(methods);
        int index = input.readInt("Способ доставки", 1, methods.size());
        printer.printQuote(service.chooseDelivery(orderId, methods.get(index - 1)));
    }

    private void assignCourier() {
        long orderId = readOrderId();
        printer.printCouriers(service.getCouriers());
        int courierId = input.readInt("Номер курьера", 1, Integer.MAX_VALUE);
        service.assignCourier(orderId, courierId);
        System.out.println("Курьер назначен.");
        printer.printOrder(service.getOrder(orderId));
    }

    private void changeStatus() {
        long orderId = readOrderId();
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i].getTitle());
        }
        int index = input.readInt("Новый статус", 1, statuses.length);
        service.changeStatus(orderId, statuses[index - 1]);
        System.out.println("Статус изменён.");
        printer.printOrder(service.getOrder(orderId));
    }

    private long readOrderId() {
        return input.readInt("Номер заказа", 1, Integer.MAX_VALUE);
    }
}