package ua.teamchallenge.survivalstore.service;

import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;

public interface CategoryService {
    void createCategory(CategoryRequest categoryRequest, MultipartFile image);
}
