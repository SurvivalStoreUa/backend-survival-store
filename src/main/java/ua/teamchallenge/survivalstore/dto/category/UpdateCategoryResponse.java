package ua.teamchallenge.survivalstore.dto.category;

public record UpdateCategoryResponse(
        Long id,
        String name,
        String image,
        Boolean isTop
) {
}
