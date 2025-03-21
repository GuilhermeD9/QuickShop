package dev.guilherme.basketservice.service;

import dev.guilherme.basketservice.client.PlatziStoreClient;
import dev.guilherme.basketservice.client.response.PlatziProductResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final PlatziStoreClient client;

    public ProductService(PlatziStoreClient client) {
        this.client = client;
    }

    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts() {
        return client.getAllProducts();
    }

    @Cacheable(value = "product", key = "#id")
    public PlatziProductResponse getProductsById(Long id) {
        return client.getProductById(id);
    }
}
