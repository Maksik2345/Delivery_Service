package delivery.model;

import delivery.exception.CourierAssignmentException;

public class Courier {
    private static long nextId = 1;
    private final long id;
    private final String name;
    private final String phone;
    private boolean available = true; //  Курьер может выполнять только один заказ

    public Courier(String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя курьера не может быть пустым");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Телефон курьера не может быть пустым");
        }
        this.id = nextId++;
        this.name = name.trim();
        this.phone = phone.trim();
    }

    public void assign() {
        if (!available) {
            throw new CourierAssignmentException("Курьер " + name + " уже занят другим заказом");
        }
        available = false;
    }

    // курьер освободился от заказа
    public void release() {
        available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}