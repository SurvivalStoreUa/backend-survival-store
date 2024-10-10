package ua.teamchallenge.survivalstore.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.product.ProductRequest;
import ua.teamchallenge.survivalstore.service.ProductService;
import ua.teamchallenge.survivalstore.validation.general.image.ImageExtensionValid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product")
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
    @PostMapping(path = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<HttpStatus> createProduct(@Valid @RequestPart ProductRequest productRequest,
                                             @RequestPart(name = "image", required = false)
                                             @NotNull(message = "Image is required")
                                             @ImageExtensionValid MultipartFile image) {
        productService.create(productRequest, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
