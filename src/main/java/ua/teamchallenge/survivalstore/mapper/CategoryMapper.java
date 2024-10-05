package ua.teamchallenge.survivalstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.entity.Category;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    @Mapping(target = "image", source = "savedImage")
    Category categoryRequestToCategory(CategoryRequest categoryRequest, String savedImage);
}
