package ua.teamchallenge.survivalstore.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.entity.Product;
import ua.teamchallenge.survivalstore.mapper.ProductMapper;
import ua.teamchallenge.survivalstore.repository.CategoryRepository;
import ua.teamchallenge.survivalstore.repository.ProductRepository;
import ua.teamchallenge.survivalstore.service.ProductService;
import ua.teamchallenge.survivalstore.util.UploadFileUtil;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UploadFileUtil uploadFileUtil;
    private final ProductMapper productMapper;

    @Override
    public Product create(ProductRequest request, MultipartFile image) {
        if (!categoryRepository.existsById(request.categoryId()))
            throw new EntityNotFoundException("Category with id " + request.categoryId() + " not found");

        String savedImage = uploadFileUtil.saveFile(image);
        Product product = productMapper.productRequestToProduct(request, savedImage);
        return productRepository.save(product);
    }
}
