package dev.guilherme.basketservice.service;

import dev.guilherme.basketservice.client.response.PlatziProductResponse;
import dev.guilherme.basketservice.controller.request.BasketRequest;
import dev.guilherme.basketservice.controller.request.PaymentRequest;
import dev.guilherme.basketservice.entity.Basket;
import dev.guilherme.basketservice.entity.Product;
import dev.guilherme.basketservice.entity.Status;
import dev.guilherme.basketservice.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    private final BasketRepository repository;
    private final ProductService productService;

    public BasketService(BasketRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public Basket createBasket(BasketRequest basketRequest) {
        repository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new IllegalArgumentException("There is alredy an open basket from this client");
                });

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductsById(productRequest.id());
            products.add(new Product(platziProductResponse.id(), platziProductResponse.title(),
                    platziProductResponse.price(), productRequest.quantity()));
        });

        Basket basket = new Basket(basketRequest.clientId(), Status.OPEN, products);

        basket.calculateTotalPrice();
        return repository.save(basket);
    }

    public Basket getBasketById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Basket not founded"));
    }

    public Basket updateBasket(String basketId, BasketRequest request) {
        Basket savedBasket = getBasketById(basketId);

        List<Product> products = new ArrayList<>();
        request.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductsById(productRequest.id());
            products.add(new Product(platziProductResponse.id(), platziProductResponse.title(),
                    platziProductResponse.price(), productRequest.quantity()));
        });

        savedBasket.setProducts(products);

        savedBasket.calculateTotalPrice();
        return repository.save(savedBasket);
    }

    public Basket payBasket(String basketId, PaymentRequest request) {
        Basket savedBasket = getBasketById(basketId);
        savedBasket.setPaymentMethod(request.getPaymentMethod());
        savedBasket.setStatus(Status.SOLD);
        return repository.save(savedBasket);
    }
}
