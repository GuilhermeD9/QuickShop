package dev.guilherme.basketservice.repository;

import dev.guilherme.basketservice.entity.Basket;
import dev.guilherme.basketservice.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BasketRepository extends MongoRepository<Basket, String> {
    Optional<Basket> findByClientAndStatus(Long client, Status status);
}
