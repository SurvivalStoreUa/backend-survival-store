package ua.teamchallenge.survivalstore.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;
import ua.teamchallenge.survivalstore.dto.category.UpdateCategoryResponse;
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
    private Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

    @Override
    public void createCategory(CategoryRequest categoryRequest, MultipartFile image) {
        logger.info("createCategory() - Creating category: {}", categoryRequest.toString());
        String savedImage = saveImage(image);
        Category category = categoryMapper.categoryRequestToCategory(categoryRequest, savedImage);
        categoryRepository.save(category);
        logger.info("createCategory() - Category has been created");
    }

    @Override
    public void updateCategory(Long id, CategoryRequest categoryRequest, MultipartFile image) {
        logger.info("updateCategory() - Updating category: {}", categoryRequest.toString());
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Category has not been found by id: %s ", id)));
        if(category.getParentCategory() != null)
            throw new EntityNotFoundException(String.format("Category has not been found by id: %s ", id));

        if(image != null && !image.isEmpty()){
            String savedImage = updateImage(image, category.getImage());
            categoryMapper.updateCategory(category, categoryRequest, savedImage);
        } else {
            categoryMapper.updateCategory(category, categoryRequest);
        }
        categoryRepository.save(category);
        logger.info("updateCategory() - Category has been updated");
    }

    private String updateImage(MultipartFile newImage, String imageInDB) {
        logger.info("updateImage() - Updating image {}", newImage.getOriginalFilename());
        if(uploadFileUtil.deleteFile(imageInDB)){
            String savedImage = saveImage(newImage);
            logger.info("updateImage() - Image has been updated");
            return savedImage;
        }
        logger.info("updateImage() - Image has not been updated");
        return null;
    }

    @Override
    public UpdateCategoryResponse getCategory(Long id) {
        logger.info("getCategory() - Getting category by id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Category has not been found by id: %s ", id)));
        if(category.getParentCategory() != null)
            throw new EntityNotFoundException(String.format("Category has not been found by id: %s ", id));

        UpdateCategoryResponse updateCategoryResponse = categoryMapper.categoryToUpdateCategoryResponse(category);
        logger.info("getCategory() - Got category");
        return updateCategoryResponse;
    }

    @Override
    public List<CategoryResponse> getMainCategories() {
        return categoryMapper.categoriesToCategoryResponses(
                categoryRepository.findAllByParentCategoryIsNull());
    }
    private String saveImage(MultipartFile image){
        logger.info("saveImage() - Saving image");
        String savedImage = null;
        if(image != null && !image.isEmpty()) {
            savedImage = uploadFileUtil.saveFile(image);
            logger.info("saveImage() - Image has been saved");
        }
        return savedImage;
    }
}
