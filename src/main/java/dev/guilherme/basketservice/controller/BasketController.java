package dev.guilherme.basketservice.controller;

import dev.guilherme.basketservice.controller.request.BasketRequest;
import dev.guilherme.basketservice.controller.request.PaymentRequest;
import dev.guilherme.basketservice.entity.Basket;
import dev.guilherme.basketservice.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketByIfd(@PathVariable String id) {
        return ResponseEntity.ok(basketService.getBasketById(id));
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(basketService.createBasket(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(basketService.updateBasket(id, request));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Basket> payBasket(@PathVariable String id, @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(basketService.payBasket(id, paymentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable String id) {
        basketService.deleteBasket(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
