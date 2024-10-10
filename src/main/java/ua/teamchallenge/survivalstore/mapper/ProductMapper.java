package ua.teamchallenge.survivalstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.dto.product.ProductResponse;
import ua.teamchallenge.survivalstore.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    @Mapping(source = "savedImage", target = "image")
    @Mapping(source = "productRequest.categoryId", target = "category.id")
    Product productRequestToProduct(ProductRequest productRequest, String savedImage);

    @Mapping(source = "category.name", target = "subcategory.name")
    @Mapping(source = "category.id", target = "subcategory.id")
    @Mapping(source = "category.parentCategory.name", target = "category.name")
    @Mapping(source = "category.parentCategory.id", target = "category.id")
    ProductResponse productToProductResponse(Product product);

    List<ProductResponse> productListToProductResponseList(List<Product> products);
}
