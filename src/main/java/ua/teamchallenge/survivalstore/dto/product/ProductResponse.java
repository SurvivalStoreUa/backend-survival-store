package ua.teamchallenge.survivalstore.dto.product;

import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        CategoryResponse category,
        CategoryResponse subcategory,
        BigDecimal price
) {
}
