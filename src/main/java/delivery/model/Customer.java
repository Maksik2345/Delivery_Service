package delivery.model;

public class Customer {
    private final long id;
    private final String name;
    private final String phone;

    public Customer(long id, String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Телефон клиента не может быть пустым");
        }
        this.id = id;
        this.name = name.trim();
        this.phone = phone.trim();
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