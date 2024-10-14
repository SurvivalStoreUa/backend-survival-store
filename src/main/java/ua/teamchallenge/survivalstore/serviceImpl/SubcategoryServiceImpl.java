package ua.teamchallenge.survivalstore.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.teamchallenge.survivalstore.dto.subcategory.SubcategoryRequest;
import ua.teamchallenge.survivalstore.entity.Category;
import ua.teamchallenge.survivalstore.mapper.SubcategoryMapper;
import ua.teamchallenge.survivalstore.repository.CategoryRepository;
import ua.teamchallenge.survivalstore.service.SubcategoryService;
@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private Logger logger = LogManager.getLogger(SubcategoryServiceImpl.class);
    @Override
    public void createSubcategory(SubcategoryRequest subcategoryRequest) {
        logger.info("createSubcategory() - Creating subcategory: {}", subcategoryRequest.toString());
        if(!categoryRepository.existsById(subcategoryRequest.categoryId()))
            throw new EntityNotFoundException(String.format("Category has not been found by id: %s ", subcategoryRequest.categoryId()));
        Category category = subcategoryMapper.subcategoryRequestToSubCategory(subcategoryRequest);
        categoryRepository.save(category);
        logger.info("createSubcategory() - Subcategory has been created");
    }
}
