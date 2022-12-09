package com.tallstech.sordman.api;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.domain.price.dto.PriceCreateDto;
import com.tallstech.sordman.domain.price.dto.PriceDto;
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


@Tag(name = "Price")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/prices")
public interface PriceApi {


    @Operation(operationId = "findPriceById", summary = "Find price by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getPriceById(@RequestHeader Map<String, String> header, @PathVariable("id") String id);

    @Operation(operationId = "getAllPricesPaginated", summary = "Get all product item prices with pagination.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PriceDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getAllPrices(@RequestHeader Map<String, String> header,
                                        @RequestParam(defaultValue = "0", required = false) int page,
                                        @RequestParam(defaultValue = "10", required = false) int size,
                                        @RequestParam(value = "sort", defaultValue = "id;DESC", required = false) String[] sortBy);

    @Operation(operationId = "createPrice", summary = "Create product item price.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> createPrice(@RequestHeader Map<String, String> header, @Valid @RequestBody PriceCreateDto priceCreateDto);

    @Operation(operationId = "updatePrice", summary = "Update product item price.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> updatePrice(@RequestHeader Map<String, String> header, @Valid @RequestBody PriceDto priceDto);

    @Operation(operationId = "bulkUpdatePrice", summary = "Update given price items.")
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
    ResponseEntity<Object> bulkUpdatePrice(@RequestHeader Map<String, String> header,
                                           @Valid @RequestBody List<PriceDto> priceDtoList,
                                           @RequestParam String field,
                                           @RequestParam Object value) throws SordmanException;


    @Operation(operationId = "deletePrice", summary = "Delete product item price.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> deletePrice(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id);

    @Operation(operationId = "bulkDeletePrice", summary = "Bulk delete price items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkDeletePrice(@RequestHeader Map<String, String> header, @NotNull @RequestBody List<String> idList) throws SordmanException;

}