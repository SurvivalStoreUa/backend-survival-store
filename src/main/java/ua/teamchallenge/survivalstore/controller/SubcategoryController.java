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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.teamchallenge.survivalstore.dto.subcategory.SubcategoryRequest;
import ua.teamchallenge.survivalstore.service.SubcategoryService;
@Tag(name = "Subcategory")
@RestController
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    @Operation(summary = "Create subcategory",description = "Creating subcategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Failed validation",content = {@Content(mediaType = "application/json",schema = @Schema(example = "{\n" + "  \"field\": \"Validation error message\"\n" + "}"))})})
    @PostMapping("/admin/subcategories")
    ResponseEntity<?> createSubcategory(@Valid @RequestBody SubcategoryRequest subcategoryRequest){
        subcategoryService.createSubcategory(subcategoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
