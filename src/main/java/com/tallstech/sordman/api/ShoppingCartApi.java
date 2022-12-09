package com.tallstech.sordman.api;

import java.util.List;
import java.util.Map;

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
import com.tallstech.sordman.domain.shoppingcart.dto.CartItemTypeDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartCreateDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartDto;
import com.tallstech.sordman.exception.SordmanError;


@Tag(name = "Shopping Cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/carts")
public interface ShoppingCartApi {

    @Operation(operationId = "findShoppingCartById", summary = "Find shopping cart by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getShoppingCartById(@RequestHeader Map<String, String> header, @PathVariable("id") String id);

    @Operation(operationId = "findActiveShoppingCartByPartyId", summary = "Find shopping cart by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(value = "/active/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getActiveShoppingCartByPartyId(@RequestHeader Map<String, String> header, @PathVariable("id") Long id);


    @Operation(operationId = "getAllShoppingCartsPaginated", summary = "Get all shopping carts with pagination.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingCartDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @GetMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> getAllShoppingCarts(@RequestHeader Map<String, String> header,
                                               @RequestParam(defaultValue = "0", required = false) int page,
                                               @RequestParam(defaultValue = "10", required = false) int size,
                                               @RequestParam(value = "sort", defaultValue = "id;DESC", required = false) String[] sortBy);

    @Operation(operationId = "createShoppingCart", summary = "Create shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> createShoppingCart(@RequestHeader Map<String, String> header, @Valid @RequestBody ShoppingCartCreateDto shoppingCartCreateDto);

    @Operation(operationId = "addItemToShoppingCart", summary = "Add item to shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(value = "/add/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> addItemToShoppingCart(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id, @Valid @RequestBody CartItemTypeDto cartItemTypeDto);

    @Operation(operationId = "removeItemFromShoppingCart", summary = "Remove item from shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/remove/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> removeItemFromShoppingCart(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id, @Valid @RequestBody CartItemTypeDto cartItemTypeDto);


    @Operation(operationId = "addCouponTomShoppingCart", summary = "Add coupon to shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PostMapping(value = "/coupon/{id}/{code}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> addCouponToShoppingCart(@RequestHeader Map<String, String> header, @NotNull @PathVariable("code") String id, @NotNull @PathVariable("code") String couponCode);


    @Operation(operationId = "updateShoppingCart", summary = "Update shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ShoppingCartDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @PutMapping(produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> updateShoppingCart(@RequestHeader Map<String, String> header, @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @Operation(operationId = "bulkUpdateShoppingCart", summary = "Update given shopping cart items.")
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
    ResponseEntity<Object> bulkUpdateShoppingCart(@RequestHeader Map<String, String> header,
                                                  @Valid @RequestBody List<ShoppingCartDto> shoppingCartDtoList,
                                                  @RequestParam String field,
                                                  @RequestParam Object value) throws SordmanException;

    @Operation(operationId = "deleteShoppingCart", summary = "Delete shopping cart.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> deleteShoppingCart(@RequestHeader Map<String, String> header, @NotNull @PathVariable("id") String id);

    @Operation(operationId = "bulkDeleteShoppingCart", summary = "Bulk delete shopping cart items.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = SordmanError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = SordmanError.class)))
    })
    @DeleteMapping(value = "/bulk", produces = {"application/json;charset=utf-8"})
    ResponseEntity<Object> bulkDeleteShoppingCart(@RequestHeader Map<String, String> header, @NotNull @RequestBody List<String> idList) throws SordmanException;
}
