package com.marchosiax.tangerine.controller;

import com.marchosiax.tangerine.dto.ProductDTO;
import com.marchosiax.tangerine.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/springresttest")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO newProduct(@RequestParam String productName) {
        LOGGER.info("Path: /, method: POST, param: productName={}, newProduct(String)", productName);
        return productService.newProduct(productName);
    }

    @GetMapping("/prid/{productId}")
    public ProductDTO findById(@PathVariable Long productId) {
        LOGGER.info("Path: /prid/{}, method: GET, findById(Long)", productId);
        return productService.getProductById(productId);
    }

    @GetMapping("/prid")
    public List<ProductDTO> findAll() {
        LOGGER.info("Path: /prid, method: GET, findAll()");
        return productService.getAllProducts();
    }

}
