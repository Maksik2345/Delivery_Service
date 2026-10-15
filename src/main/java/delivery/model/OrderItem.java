package delivery.model;

import java.math.BigDecimal;

public class OrderItem {
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(String productName, int quantity, BigDecimal unitPrice) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Название товара не может быть пустым");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше 0");
        }
        if (unitPrice ==  null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductName(){
        return productName;
    }

    public int getQuantity(){
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotalPrice(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
