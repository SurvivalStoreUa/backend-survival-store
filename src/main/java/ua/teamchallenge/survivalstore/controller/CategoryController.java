package ua.teamchallenge.survivalstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.teamchallenge.survivalstore.dto.category.CategoryRequest;
import ua.teamchallenge.survivalstore.dto.category.CategoryResponse;
import ua.teamchallenge.survivalstore.service.CategoryService;
import ua.teamchallenge.survivalstore.validation.general.image.ImageExtensionValid;

import java.util.List;

@Tag(name = "Category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create category", description = "Creating category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Failed validation", content = {@Content(mediaType = "application/json", schema = @Schema(example = "{\n\"name\": \"Field must not be empty\"\n}"))})
    })
    @PostMapping(path = "/admin/category/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> createCategory(@Valid @RequestPart CategoryRequest categoryRequest,
                                     @RequestPart(name = "image", required = false) @ImageExtensionValid MultipartFile image) {
        categoryService.createCategory(categoryRequest, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get main categories", description = "Retrieves a list of main categories without parent category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})
    })
    @GetMapping(path = "/admin/category/main")
    public List<CategoryResponse> getMainCategories() {
        return categoryService.getMainCategories();
    }
}
