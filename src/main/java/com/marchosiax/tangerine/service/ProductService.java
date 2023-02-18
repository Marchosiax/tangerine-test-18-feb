package com.marchosiax.tangerine.service;

import com.marchosiax.tangerine.dto.ProductDTO;
import com.marchosiax.tangerine.exception.ProductNotFoundException;
import com.marchosiax.tangerine.model.Product;
import com.marchosiax.tangerine.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final Cache cache;

    public ProductService(ProductRepository repository, Cache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    /**
     * Insert new product into database
     *
     * @param name The name of the product
     * @return Saved product DTO
     */
    public ProductDTO newProduct(String name) {
        LOGGER.info("Inserting new product with name {}", name);
        var product = repository.save(new Product(name));
        cache.putIfAbsent(product.getId(), product);
        return new ProductDTO(product.getId(), product.getName());
    }

    /**
     * Find a product with requested id and return product DTO
     *
     * @param id Requested id
     * @return Product DTO
     * @throws IllegalStateException if no product was found
     */
    public ProductDTO getProductById(Long id) throws IllegalStateException {
        Product product = null;
        var wrapper = cache.get(id);
        if (wrapper != null)
            product = (Product) wrapper.get();
        if (product == null) {
            LOGGER.warn("Product with id {} was not found", id);
            product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            cache.putIfAbsent(product.getId(), product);
        } else
            LOGGER.debug("Fetched product from cache");

        return new ProductDTO(product.getId(), product.getName());
    }

    /**
     * Find all the products in database
     *
     * @return A list of products as DTOs
     */
    public List<ProductDTO> getAllProducts() {
        LOGGER.info("Getting all products from database");
        return repository.findAll()
                .stream()
                .map(p -> new ProductDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
