package com.tallstech.sordman.api;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.domain.salesorder.dto.SalesOrderCreateDto;
import com.tallstech.sordman.domain.salesorder.dto.SalesOrderDto;
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


@Tag(name = "Sales Order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/orders/sales")
public interface SalesOrderApi {

    @Operation(operationId = "findSalesOrderById", summary = "Find sales order item by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = SalesOrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getSalesOrderById(@RequestHeader Map<String, String> header, @PathVariable("id") String id);


    @Operation(operationId = "getAllSalesOrdersPaginated", summary = "Get all sales orders with pagination.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SalesOrderDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getAllSalesOrders(@RequestHeader Map<String, String> header,
                                             @RequestParam(defaultValue = "0", required = false) int page,
                                             @RequestParam(defaultValue = "10", required = false) int size,
                                             @RequestParam(value = "sort", defaultValue = "id;DESC", required = false) String[] sortBy);

    @Operation(operationId = "createSalesOrder", summary = "Create sales order.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = SalesOrderDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = SalesOrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> createSalesOrder(@RequestHeader Map<String, String> header, @Valid @RequestBody SalesOrderCreateDto salesOrderCreateDto);

    @Operation(operationId = "updateSalesOrder", summary = "Update sales order.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = SalesOrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> updateSalesOrder(@RequestHeader Map<String, String> header, @Valid @RequestBody SalesOrderDto salesOrderDto);

    @Operation(operationId = "bulkUpdateSalesOrder", summary = "Update given sales order items.")
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
    ResponseEntity<Object> bulkUpdateSalesOrder(@RequestHeader Map<String, String> header,
                                                @Valid @RequestBody List<SalesOrderDto> salesOrderDtoList,
                                                @RequestParam String field,
                                                @RequestParam Object value) throws SordmanException;


    @Operation(operationId = "deleteSalesOrder", summary = "Delete sales order.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> deleteSalesOrder(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id);

    @Operation(operationId = "bulkDeleteSalesOrder", summary = "Bulk delete sales order items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkDeleteSalesOrder(@RequestHeader Map<String, String> header, @NotNull @RequestBody List<String> idList) throws SordmanException;
}
