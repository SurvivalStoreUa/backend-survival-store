package ua.teamchallenge.survivalstore.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;
import ua.teamchallenge.survivalstore.entity.Category;
import ua.teamchallenge.survivalstore.mapper.CategoryMapper;
import ua.teamchallenge.survivalstore.repository.CategoryRepository;
import ua.teamchallenge.survivalstore.service.CategoryService;
import ua.teamchallenge.survivalstore.util.UploadFileUtil;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public void createCategory(CategoryRequest categoryRequest, MultipartFile image) {
        String savedImage = uploadFileUtil.saveFile(image);
        Category category = categoryMapper.categoryRequestToCategory(categoryRequest, savedImage);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getMainCategories() {
        return categoryMapper.categoriesToCategoryResponses(
                categoryRepository.findAllByParentCategoryIsNull());
    }
}
