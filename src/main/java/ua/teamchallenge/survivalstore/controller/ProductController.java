package ua.teamchallenge.survivalstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.dto.product.ProductResponse;
import ua.teamchallenge.survivalstore.service.ProductService;
import ua.teamchallenge.survivalstore.validation.general.image.ImageExtensionValid;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create new product", description = "Creating new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\n\"field\": \"Validation error message\"\n}"))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\n\"message\": \"Category with id X not found\"\n}")))
    })
    @PostMapping(path = "/admin/product/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<HttpStatus> createProduct(@Valid @RequestPart ProductRequest productRequest,
                                             @RequestPart(name = "image", required = false)
                                             @NotNull(message = "Image is required")
                                             @ImageExtensionValid MultipartFile image) {
        productService.create(productRequest, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of products with search by name, filters, and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\n  \"parameter\": \"Invalid value for parameter. Expected type: X\"\n}"))),
    })
    @Parameters({
            @Parameter(name = "page", description = "Page number for pagination (starting from 0)", schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "Number of products per page", schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "name", description = "Search products by name (optional)", schema = @Schema(type = "string")),
            @Parameter(name = "category", description = "Filter by category ID (optional)", schema = @Schema(type = "integer")),
            @Parameter(name = "subcategory", description = "Filter by subcategory ID (optional)", schema = @Schema(type = "integer")),
            @Parameter(name = "priceFrom", description = "Filter products with price greater than or equal to this value (optional)", schema = @Schema(type = "number", format = "bigdecimal")),
            @Parameter(name = "priceTo", description = "Filter products with price less than or equal to this value (optional)", schema = @Schema(type = "number", format = "bigdecimal"))
    })
    @GetMapping(path = "/admin/product")
    public ResponseEntity<List<ProductResponse>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "subcategory", required = false) Long subcategoryId,
            @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom,
            @RequestParam(value = "priceTo", required = false) BigDecimal priceTo) {
        return ResponseEntity.ok(productService.findAll(name, categoryId, subcategoryId, priceFrom, priceTo, page, size));
    }


}
