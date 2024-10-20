package ua.teamchallenge.survivalstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;
import ua.teamchallenge.survivalstore.dto.category.UpdateCategoryResponse;
import ua.teamchallenge.survivalstore.entity.Category;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    @Mapping(target = "image", source = "savedImage")
    Category categoryRequestToCategory(CategoryRequest categoryRequest, String savedImage);
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
    @Mapping(target = "image", source = "savedImage")
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest, String savedImage);
    @Mapping(target = "image", expression = "java(createImageUrl(category))")
    UpdateCategoryResponse categoryToUpdateCategoryResponse(Category category);
    default String createImageUrl(Category category){
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUrl+"/uploads/"+category.getImage();
    }
    List<CategoryResponse> categoriesToCategoryResponses(List<Category> categories);
}
