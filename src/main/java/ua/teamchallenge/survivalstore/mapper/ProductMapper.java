package ua.teamchallenge.survivalstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.entity.Product;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    @Mapping(source = "savedImage", target = "image")
    @Mapping(source = "productRequest.categoryId", target = "category.id")
    Product productRequestToProduct(ProductRequest productRequest, String savedImage);
}
