package ua.teamchallenge.survivalstore.dto.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubcategoryRequest(
        @Schema(example = "Generator",maxLength = 100)
        @Size(max = 100, message = "The field must be no longer than 100 characters")
        @NotBlank(message = "Field must not be empty")
        String name,
        @Schema(example = "2")
        @NotNull(message = "Field must not be empty")
        Long categoryId
) {
}
