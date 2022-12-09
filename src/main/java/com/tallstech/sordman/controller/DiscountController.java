package com.tallstech.sordman.controller;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.DiscountApi;
import com.tallstech.sordman.domain.discount.DiscountServiceImpl;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.discount.dto.DiscountCreateDto;
import com.tallstech.sordman.domain.discount.dto.DiscountDto;
import com.tallstech.sordman.domain.discount.mapper.DiscountMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class DiscountController implements DiscountApi {

    private DiscountMapper discountMapper;
    private DiscountServiceImpl discountService;

    public DiscountController(DiscountMapper discountMapper, DiscountServiceImpl discountService) {
        this.discountMapper = discountMapper;
        this.discountService = discountService;
    }

    @Override
    public ResponseEntity<Object> getDiscountById(Map<String, String> header, String id) {
        var discount = discountService.getById(id);
        var discountDto = discountMapper.toDto(discount);
        return ResponseEntity.ok(discountDto);
    }

    @Override
    public ResponseEntity<Object> getAllDiscounts(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<DiscountDto> pageOfDiscounts = discountService.getAllPaginated(page, size, sortBy)
                .map(discount -> discountMapper.toDto(discount));
        return ResponseEntity.ok(pageOfDiscounts);
    }

    @Override
    public ResponseEntity<Object> createDiscount(Map<String, String> header, DiscountCreateDto discountCreateDto) {
        var discount = discountMapper.toDoc(discountCreateDto);
        var discountDto = discountMapper.toDto(discountService.create(discount));
        return ResponseEntity.ok(discountDto);
    }

    @Override
    public ResponseEntity<Object> updateDiscount(Map<String, String> header, DiscountDto discountDto) {
        var discount = discountService.getById(discountDto.id());
        discountMapper.toDocForPatch(discountDto, discount);
        return ResponseEntity.ok(discountService.update(discount));
    }

    @Override
    public ResponseEntity<Object> bulkUpdateDiscount(Map<String, String> header, List<DiscountDto> discountDtoList, String field, Object value) throws SordmanException {
        List<Discount> discountList = discountMapper.toDocList(discountDtoList);
        return ResponseEntity.ok(discountService.bulkUpdate(discountList, field, value));
    }

    @Override
    public ResponseEntity<Object> deleteDiscount(Map<String, String> header, String id) {
        return ResponseEntity.ok(discountService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeleteDiscount(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(discountService.bulkDelete(idList));
    }
}
