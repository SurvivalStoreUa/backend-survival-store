package ua.teamchallenge.survivalstore.service;

import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.entity.Product;

public interface ProductService {
    Product create(ProductRequest request, MultipartFile image);
}
