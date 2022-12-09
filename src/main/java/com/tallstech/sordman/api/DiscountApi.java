package com.tallstech.sordman.api;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.domain.discount.dto.DiscountCreateDto;
import com.tallstech.sordman.domain.discount.dto.DiscountDto;
import com.tallstech.sordman.exception.SordmanException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tallstech.sordman.exception.SordmanError;


@Tag(name = "Discount")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/discounts")
public interface DiscountApi {

    @Operation(operationId = "findDiscountById", summary = "Find discount by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = DiscountDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getDiscountById(@RequestHeader Map<String, String> header, @PathVariable("id") String id);

    @Operation(operationId = "getAllDiscountsPaginated", summary = "Get all discount items with pagination.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DiscountDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getAllDiscounts(@RequestHeader Map<String, String> header,
                                           @RequestParam(defaultValue = "0", required = false) int page,
                                           @RequestParam(defaultValue = "10", required = false) int size,
                                           @RequestParam(value = "sort", defaultValue = "id;DESC", required = false) String[] sortBy);

    @Operation(operationId = "createDiscount", summary = "Create discount item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = DiscountDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DiscountDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> createDiscount(@RequestHeader Map<String, String> header, @Valid @RequestBody DiscountCreateDto discountCreateDto);

    @Operation(operationId = "updateDiscount", summary = "Update discount item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = DiscountDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> updateDiscount(@RequestHeader Map<String, String> header, @Valid @RequestBody DiscountDto discountDto);

    @Operation(operationId = "bulkUpdateDiscount", summary = "Update given discount items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkUpdateDiscount(@RequestHeader Map<String, String> header,
                                              @Valid @RequestBody List<DiscountDto> discountDtoList,
                                              @RequestParam String field,
                                              @RequestParam Object value) throws SordmanException;

    @Operation(operationId = "deleteDiscount", summary = "Delete discount item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> deleteDiscount(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id);

    @Operation(operationId = "bulkDeleteDiscount", summary = "Bulk delete discount items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkDeleteDiscount(@RequestHeader Map<String, String> header, @NotNull @RequestBody List<String> idList) throws SordmanException;
}
