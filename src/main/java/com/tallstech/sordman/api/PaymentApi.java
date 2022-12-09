package com.tallstech.sordman.api;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.domain.payment.dto.PaymentCreateDto;
import com.tallstech.sordman.domain.payment.dto.PaymentDto;
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


@Tag(name = "Payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/payments")
public interface PaymentApi {

    @Operation(operationId = "findPaymentById", summary = "Find payment by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/{id}", produces = {" "})
    ResponseEntity<Object> getPaymentById(@RequestHeader Map<String, String> header, @PathVariable("id") String id);

    @Operation(operationId = "getAllPaymentsPaginated", summary = "Get all payment items with pagination.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getAllPayments(@RequestHeader Map<String, String> header,
                                          @RequestParam(defaultValue = "0", required = false) int page,
                                          @RequestParam(defaultValue = "10", required = false) int size,
                                          @RequestParam(value = "sort", defaultValue = "id;DESC", required = false) String[] sortBy);

    @Operation(operationId = "startCheckoutForm", summary = "Start checkout form that provided by the payment integrator")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(value = "/checkout/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> startCheckoutForm(@RequestHeader Map<String, String> header,@NotNull @PathVariable("id") String salesOrderId);

    @Operation(operationId = "createPayment", summary = "Create payment item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> createPayment(@RequestHeader Map<String, String> header, @Valid @RequestBody PaymentCreateDto paymentCreateDto);

    @Operation(operationId = "updatePayment", summary = "Update payment item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> updatePayment(@RequestHeader Map<String, String> header, @Valid @RequestBody PaymentDto paymentDto);

    @Operation(operationId = "bulkUpdatePayment", summary = "Update given payment items.")
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
    ResponseEntity<Object> bulkUpdatePayment(@RequestHeader Map<String, String> header,
                                             @Valid @RequestBody List<PaymentDto> paymentDtoList,
                                             @RequestParam String field,
                                             @RequestParam Object value) throws SordmanException;


    @Operation(operationId = "deletePayment", summary = "Delete payment item.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> deletePayment(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id);

    @Operation(operationId = "bulkDeletePayment", summary = "Bulk delete payment items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkDeletePayment(@RequestHeader Map<String, String> header, @NotNull @RequestBody List<String> idList) throws SordmanException;
}
