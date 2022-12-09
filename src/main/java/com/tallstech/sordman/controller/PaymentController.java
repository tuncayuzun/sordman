package com.tallstech.sordman.controller;

import static com.tallstech.sordman.util.MongoUtils.generatePageMap;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.PaymentApi;
import com.tallstech.sordman.domain.payment.PaymentServiceImpl;
import com.tallstech.sordman.domain.payment.document.Payment;
import com.tallstech.sordman.domain.payment.dto.PaymentCreateDto;
import com.tallstech.sordman.domain.payment.dto.PaymentDto;
import com.tallstech.sordman.domain.payment.mapper.PaymentMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PaymentController implements PaymentApi {

    private PaymentMapper paymentMapper;
    private PaymentServiceImpl paymentService;

    public PaymentController(PaymentMapper paymentMapper, PaymentServiceImpl paymentService) {
        this.paymentMapper = paymentMapper;
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<Object> getPaymentById(Map<String, String> header, String id) {
        var payment = paymentService.getById(id);
        var paymentDto = paymentMapper.toDto(payment);
        return ResponseEntity.ok(paymentDto);
    }

    @Override
    public ResponseEntity<Object> getAllPayments(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<Payment> pageOfPayments = paymentService.getAllPaginated(page, size, sortBy);
        List<PaymentDto> paymentDtoList = paymentMapper.toDtoList(pageOfPayments.getContent());
        return ResponseEntity.ok(generatePageMap(paymentDtoList, pageOfPayments));
    }

    @Override
    public ResponseEntity<Object> startCheckoutForm(Map<String, String> header, String salesOrderId) {
        return ResponseEntity.ok(paymentService.createPaymentInitializeForm(salesOrderId));
    }

    @Override
    public ResponseEntity<Object> createPayment(Map<String, String> header, PaymentCreateDto paymentCreateDto) {
        var payment = paymentMapper.toDoc(paymentCreateDto);
        var paymentDto = paymentMapper.toDto(paymentService.create(payment));
        return ResponseEntity.ok(paymentDto);
    }

    @Override
    public ResponseEntity<Object> updatePayment(Map<String, String> header, PaymentDto paymentDto) {
        var payment = paymentService.getById(paymentDto.id());
        paymentMapper.toDocForPatch(paymentDto, payment);
        return ResponseEntity.ok(paymentService.update(payment));
    }

    @Override
    public ResponseEntity<Object> bulkUpdatePayment(Map<String, String> header, List<PaymentDto> paymentDtoList, String field, Object value) throws SordmanException {
        List<Payment> discountList = paymentMapper.toDocList(paymentDtoList);
        return ResponseEntity.ok(paymentService.bulkUpdate(discountList, field, value));
    }

    @Override
    public ResponseEntity<Object> deletePayment(Map<String, String> header, String id) {
        return ResponseEntity.ok(paymentService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeletePayment(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(paymentService.bulkDelete(idList));
    }
}
