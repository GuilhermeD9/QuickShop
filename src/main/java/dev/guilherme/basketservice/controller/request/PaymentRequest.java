package dev.guilherme.basketservice.controller.request;

import dev.guilherme.basketservice.entity.PaymentMethod;

public class PaymentRequest {
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
