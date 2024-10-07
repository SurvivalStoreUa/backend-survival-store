package ua.teamchallenge.survivalstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @Schema(example = "Electricity", maxLength = 100)
        @Size(max = 100, message = "The field must be no longer than 100 characters")
        @NotBlank(message = "Field must not be empty")
        String name,
        @Schema(example = "false")
        @NotNull(message = "Field must not be empty")
        Boolean isTop
) {
}
