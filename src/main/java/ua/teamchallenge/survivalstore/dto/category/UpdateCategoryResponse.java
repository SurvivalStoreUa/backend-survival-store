package ua.teamchallenge.survivalstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category response for update")
public record UpdateCategoryResponse(
        @Schema(example = "1", minimum = "1")
        Long id,
        @Schema(example = "Electricity", maxLength = 100)
        String name,
        @Schema(example = "http://localhost:8080/uploads/imageName.png")
        String image,
        @Schema(example = "false")
        Boolean isTop
) {
}
