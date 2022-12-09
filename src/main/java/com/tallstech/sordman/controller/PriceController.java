package com.tallstech.sordman.controller;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.PriceApi;
import com.tallstech.sordman.domain.price.PriceServiceImpl;
import com.tallstech.sordman.domain.price.document.Price;
import com.tallstech.sordman.domain.price.dto.PriceCreateDto;
import com.tallstech.sordman.domain.price.dto.PriceDto;
import com.tallstech.sordman.domain.price.mapper.PriceMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PriceController implements PriceApi {

    private PriceMapper priceMapper;
    private PriceServiceImpl priceService;

    @Autowired
    public PriceController(PriceMapper priceMapper, PriceServiceImpl priceService) {
        this.priceMapper = priceMapper;
        this.priceService = priceService;
    }

    @Override
    public ResponseEntity<Object> getPriceById(Map<String, String> header, String id) {
        var price = priceService.getById(id);
        var priceDto = priceMapper.toDto(price);
        return ResponseEntity.ok(priceDto);
    }

    @Override
    public ResponseEntity<Object> getAllPrices(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<PriceDto> pageOfPrices = priceService.getAllPaginated(page, size, sortBy)
                .map(price -> priceMapper.toDto(price));
        return ResponseEntity.ok(pageOfPrices);
    }

    @Override
    public ResponseEntity<Object> createPrice(Map<String, String> header, PriceCreateDto priceCreateDto) {
        var price = priceMapper.toDoc(priceCreateDto);
        var priceDto = priceMapper.toDto(priceService.create(price));
        return ResponseEntity.ok(priceDto);
    }

    @Override
    public ResponseEntity<Object> updatePrice(Map<String, String> header, PriceDto priceDto) {
        var price = priceService.getById(priceDto.id());
        priceMapper.toDocForPatch(priceDto, price);
        return ResponseEntity.ok(priceService.update(price));
    }

    @Override
    public ResponseEntity<Object> bulkUpdatePrice(Map<String, String> header, List<PriceDto> priceDtoList, String field, Object value) throws SordmanException {
        List<Price> priceList = priceMapper.toDocList(priceDtoList);
        return ResponseEntity.ok(priceService.bulkUpdate(priceList, field, value));
    }


    @Override
    public ResponseEntity<Object> deletePrice(Map<String, String> header, String id) {
        return ResponseEntity.ok(priceService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeletePrice(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(priceService.bulkDelete(idList));
    }


}
