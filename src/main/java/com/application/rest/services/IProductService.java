package com.application.rest.services;


import com.application.rest.entities.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    void save(Product product);

    void deleteById (Long id);


}
