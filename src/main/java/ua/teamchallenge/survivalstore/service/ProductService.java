package ua.teamchallenge.survivalstore.service;

import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.dto.product.ProductResponse;
import ua.teamchallenge.survivalstore.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product create(ProductRequest request, MultipartFile image);
    List<ProductResponse> findAll(String name, Long categoryId, Long subcategoryId,
                                  BigDecimal priceFrom, BigDecimal priceTo, int page, int size);
}
