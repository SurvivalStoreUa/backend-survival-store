package ua.teamchallenge.survivalstore.dto.product;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Product request data")
public record ProductRequest(
        @Schema(description = "Product name", example = "Bicycle")
        @NotBlank(message = "Product name must not be empty")
        @Size(max = 100, message = "Product name must be less than 100 characters")
        String name,

        @Schema(description = "Product price", example = "299.99")
        @NotNull(message = "Price must not be empty")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 38, fraction = 2, message = "Price must be a valid monetary amount with up to 2 decimal places")
        BigDecimal price,

        @Schema(description = "Product description", example = "A bicycle is a vehicle with two wheels")
        @NotBlank(message = "Description must not be empty")
        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        @Schema(description = "Category ID", example = "1")
        @NotNull(message = "Category ID is required")
        Long categoryId
) {
}
