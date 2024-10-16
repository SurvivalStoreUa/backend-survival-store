package ua.teamchallenge.survivalstore.service;

import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequest categoryRequest, MultipartFile image);
    void updateCategory(Long id, CategoryRequest categoryRequest, MultipartFile image);
    List<CategoryResponse> getMainCategories();
}
