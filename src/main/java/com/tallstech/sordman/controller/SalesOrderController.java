package com.tallstech.sordman.controller;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.SalesOrderApi;
import com.tallstech.sordman.domain.salesorder.SalesOrderServiceImpl;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
import com.tallstech.sordman.domain.salesorder.dto.SalesOrderCreateDto;
import com.tallstech.sordman.domain.salesorder.dto.SalesOrderDto;
import com.tallstech.sordman.domain.salesorder.mapper.SalesOrderMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SalesOrderController implements SalesOrderApi {

    private SalesOrderMapper salesOrderMapper;
    private SalesOrderServiceImpl salesOrderService;

    @Autowired
    public SalesOrderController(SalesOrderMapper salesOrderMapper, SalesOrderServiceImpl salesOrderService) {
        this.salesOrderMapper = salesOrderMapper;
        this.salesOrderService = salesOrderService;
    }

    @Override
    public ResponseEntity<Object> getSalesOrderById(Map<String, String> header, String id) {
        var salesOrder = salesOrderService.getById(id);
        var salesOrderDto = salesOrderMapper.toDto(salesOrder);
        return ResponseEntity.ok(salesOrderDto);
    }

    @Override
    public ResponseEntity<Object> getAllSalesOrders(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<SalesOrderDto> pageOfSalesOrders = salesOrderService.getAllPaginated(page, size, sortBy)
                .map(salesOrder -> salesOrderMapper.toDto(salesOrder));
        return ResponseEntity.ok(pageOfSalesOrders);
    }

    @Override
    public ResponseEntity<Object> createSalesOrder(Map<String, String> header, SalesOrderCreateDto salesOrderCreateDto) {
        var salesOrder = salesOrderMapper.toDoc(salesOrderCreateDto);
        var salesOrderDto = salesOrderMapper.toDto(salesOrderService.createSalesOrder(salesOrder, header));
        return ResponseEntity.ok(salesOrderDto);
    }

    @Override
    public ResponseEntity<Object> updateSalesOrder(Map<String, String> header, SalesOrderDto salesOrderDto) {
        var salesOrder = salesOrderService.getById(salesOrderDto.id());
        salesOrderMapper.toDocForPatch(salesOrderDto, salesOrder);
        return ResponseEntity.ok(salesOrderService.update(salesOrder));
    }

    @Override
    public ResponseEntity<Object> bulkUpdateSalesOrder(Map<String, String> header, List<SalesOrderDto> salesOrderDtoList, String field, Object value) throws SordmanException {
        List<SalesOrder> salesOrderList = salesOrderMapper.toDocList(salesOrderDtoList);
        return ResponseEntity.ok(salesOrderService.bulkUpdate(salesOrderList, field, value));
    }

    @Override
    public ResponseEntity<Object> deleteSalesOrder(Map<String, String> header, String id) {
        return ResponseEntity.ok(salesOrderService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeleteSalesOrder(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(salesOrderService.bulkDelete(idList));
    }
}
