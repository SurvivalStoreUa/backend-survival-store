package ua.teamchallenge.survivalstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.teamchallenge.survivalstore.dto.subcategory.SubcategoryRequest;
import ua.teamchallenge.survivalstore.entity.Category;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SubcategoryMapper {
    @Mapping(target = "parentCategory.id", source = "subcategoryRequest.categoryId")
    Category subcategoryRequestToSubCategory(SubcategoryRequest subcategoryRequest);
}
