package com.tallstech.sordman.controller;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.ProductCatalogApi;
import com.tallstech.sordman.domain.productcatalog.ProductCatalogServiceImpl;
import com.tallstech.sordman.domain.productcatalog.document.ProductCatalog;
import com.tallstech.sordman.domain.productcatalog.dto.ProductCatalogCreateDto;
import com.tallstech.sordman.domain.productcatalog.dto.ProductCatalogDto;
import com.tallstech.sordman.domain.productcatalog.mapper.ProductCatalogMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class ProductCatalogController implements ProductCatalogApi {

    private ProductCatalogMapper productCatalogMapper;
    private ProductCatalogServiceImpl productCatalogService;

    @Autowired
    public ProductCatalogController(ProductCatalogMapper productCatalogMapper, ProductCatalogServiceImpl productCatalogService) {
        this.productCatalogMapper = productCatalogMapper;
        this.productCatalogService = productCatalogService;
    }


    @Override
    public ResponseEntity<Object> getProductCatalogItemById(Map<String, String> header, String id) {
        var productCatalog = productCatalogService.getById(id);
        var productCatalogDto = productCatalogMapper.toDto(productCatalog);
        return ResponseEntity.ok(productCatalogDto);
    }

    @Override
    public ResponseEntity<Object> getOnSaleProductCatalogItems(Map<String, String> header) {
        List<ProductCatalog> productCatalogList = productCatalogService.getAllCatalogItemsWithCurrentPrice();
        List<ProductCatalogDto> productCatalogDtoList = productCatalogMapper.toDtoList(productCatalogList);
        return ResponseEntity.ok(productCatalogDtoList);
    }

    @Override
    public ResponseEntity<Object> getAllProductCatalogItems(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<ProductCatalogDto> pageOfProductCatalogs = productCatalogService.getAllPaginated(page, size, sortBy)
                .map(productCatalog -> productCatalogMapper.toDto(productCatalog));
        return ResponseEntity.ok(pageOfProductCatalogs);
    }

    @Override
    public ResponseEntity<Object> createProductCatalogItem(Map<String, String> header, ProductCatalogCreateDto productCatalogCreateDto) {
        var productCatalog = productCatalogMapper.toDoc(productCatalogCreateDto);
        var productCatalogDto = productCatalogMapper.toDto(productCatalogService.create(productCatalog));
        return ResponseEntity.ok(productCatalogDto);
    }

    @Override
    public ResponseEntity<Object> updateProductCatalogItem(Map<String, String> header, ProductCatalogDto productCatalogDto) {
        var productCatalog = productCatalogMapper.toDoc(productCatalogDto);
        var productCatalogDtoUpdated = productCatalogMapper.toDto(productCatalogService.update(productCatalog));
        return ResponseEntity.ok(productCatalogDtoUpdated);
    }

    @Override
    public ResponseEntity<Object> patchProductCatalogItem(Map<String, String> header, ProductCatalogDto productCatalogDto) {
        var productCatalog = productCatalogService.getById(productCatalogDto.id());
        productCatalogMapper.toDocForPatch(productCatalogDto, productCatalog);
        var productCatalogPatched = productCatalogService.update(productCatalog);
        return ResponseEntity.ok(productCatalogMapper.toDto(productCatalogPatched));
    }

    @Override
    public ResponseEntity<Object> bulkUpdateCatalogItem(Map<String, String> header, List<ProductCatalogDto> productCatalogDtoList, String field, Object value) throws SordmanException {
        List<ProductCatalog> productCatalogList = productCatalogMapper.toDocList(productCatalogDtoList);
        return ResponseEntity.ok(productCatalogService.bulkUpdate(productCatalogList, field, value));
    }

    @Override
    public ResponseEntity<Object> deleteProductCatalogItem(Map<String, String> header, String id) {
        return ResponseEntity.ok(productCatalogService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeleteProductCatalogItem(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(productCatalogService.bulkDelete(idList));
    }
}
