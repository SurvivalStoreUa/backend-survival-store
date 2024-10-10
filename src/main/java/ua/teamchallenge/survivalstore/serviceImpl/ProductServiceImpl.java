package ua.teamchallenge.survivalstore.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.dto.product.ProductResponse;
import ua.teamchallenge.survivalstore.entity.Product;
import ua.teamchallenge.survivalstore.mapper.ProductMapper;
import ua.teamchallenge.survivalstore.repository.CategoryRepository;
import ua.teamchallenge.survivalstore.repository.ProductRepository;
import ua.teamchallenge.survivalstore.service.ProductService;
import ua.teamchallenge.survivalstore.util.UploadFileUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ProductResponse> findAll(String name, Long categoryId, Long subcategoryId,
                                         BigDecimal priceFrom, BigDecimal priceTo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productRepository.findAll(
                (root, query, criteriaBuilder) ->
                        buildPredicates(root, criteriaBuilder, name, categoryId, subcategoryId, priceFrom, priceTo),
                pageable
        ).getContent();

        return productMapper.productListToProductResponseList(products);
    }

    private Predicate buildPredicates(Root<Product> root, CriteriaBuilder criteriaBuilder, String name,
                                      Long categoryId, Long subcategoryId, BigDecimal priceFrom, BigDecimal priceTo) {
        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(name)
                .filter(n -> !n.isEmpty())
                .ifPresent(n -> predicates.add(criteriaBuilder.like(root.get("name"), "%" + n + "%")));
        Optional.ofNullable(categoryId)
                .ifPresent(id -> predicates.add(criteriaBuilder.equal(root.get("category").get("parentCategory").get("id"), id)));
        Optional.ofNullable(subcategoryId)
                .ifPresent(id -> predicates.add(criteriaBuilder.equal(root.get("category").get("id"), id)));
        Optional.ofNullable(priceFrom)
                .ifPresent(pf -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), pf)));
        Optional.ofNullable(priceTo)
                .ifPresent(pt -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), pt)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
