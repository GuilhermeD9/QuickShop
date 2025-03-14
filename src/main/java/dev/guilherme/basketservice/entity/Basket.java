package dev.guilherme.basketservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "basket")
public class Basket {
    @Id
    private String id;
    private Long client;
    private BigDecimal totalPrice;
    private List<Product> products;
    private Status status;
    private PaymentMethod paymentMethod;

    public void calculateTotalPrice() {
        this.totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Basket(String id, Long client, BigDecimal totalPrice, List<Product> products, Status status) {
        this.id = id;
        this.client = client;
        this.totalPrice = totalPrice;
        this.products = products;
        this.status = status;
    }

    public Basket(String id, Long client, BigDecimal totalPrice, List<Product> products, Status status, PaymentMethod paymentMethod) {
        this(id, client, totalPrice, products, status);
        this.paymentMethod = paymentMethod;
    }

    public Basket(Long client, Status status, List<Product> products) {
        this.client = client;
        this.status = status;
        this.products = products;
    }

    public Basket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
