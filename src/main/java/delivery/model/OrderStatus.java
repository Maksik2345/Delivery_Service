package delivery.model;

public enum OrderStatus {
    CREATED("Создан"),
    CONFIRMED("Подтверждён"),
    IN_DELIVERY("В доставке"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case CREATED -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED -> next == IN_DELIVERY || next == CANCELLED;
            case IN_DELIVERY -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}