package com.tallstech.sordman.controller;

import static com.tallstech.sordman.util.MongoUtils.generatePageMap;

import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.SubscriptionApi;
import com.tallstech.sordman.domain.subscription.SubscriptionServiceImpl;
import com.tallstech.sordman.domain.subscription.document.Subscription;
import com.tallstech.sordman.domain.subscription.dto.SubscriptionCreateDto;
import com.tallstech.sordman.domain.subscription.dto.SubscriptionDto;
import com.tallstech.sordman.domain.subscription.mapper.SubscriptionMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SubscriptionController implements SubscriptionApi {

    private SubscriptionMapper subscriptionMapper;
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionMapper subscriptionMapper, SubscriptionServiceImpl subscriptionService) {
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public ResponseEntity<Object> getSubscriptionById(Map<String, String> header, String id) {
        var subscription = subscriptionService.getById(id);
        var subscriptionDto = subscriptionMapper.toDto(subscription);
        return ResponseEntity.ok(subscriptionDto);
    }

    @Override
    public ResponseEntity<Object> getAllSubscriptions(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<Subscription> pageOfSubscriptions = subscriptionService.getAllPaginated(page, size, sortBy);
        List<SubscriptionDto> subscriptionDtoList = subscriptionMapper.toDtoList(pageOfSubscriptions.getContent());
        return ResponseEntity.ok(generatePageMap(subscriptionDtoList, pageOfSubscriptions));
    }

    @Override
    public ResponseEntity<Object> createSubscription(Map<String, String> header, SubscriptionCreateDto subscriptionCreateDto) {
        var subscription = subscriptionMapper.toDoc(subscriptionCreateDto);
        var subscriptionDto = subscriptionMapper.toDto(subscriptionService.create(subscription));
        return ResponseEntity.ok(subscriptionDto);
    }

    @Override
    public ResponseEntity<Object> updateSubscription(Map<String, String> header, SubscriptionDto subscriptionDto) {
        var subscription = subscriptionService.getById(subscriptionDto.id());
        subscriptionMapper.toDocForPatch(subscriptionDto, subscription);
        return ResponseEntity.ok(subscriptionService.update(subscription));
    }

    @Override
    public ResponseEntity<Object> bulkUpdateSubscription(Map<String, String> header, List<SubscriptionDto> subscriptionDtoList, String field, Object value) throws SordmanException {
        List<Subscription> subscriptionList = subscriptionMapper.toDocList(subscriptionDtoList);
        return ResponseEntity.ok(subscriptionService.bulkUpdate(subscriptionList, field, value));
    }

    @Override
    public ResponseEntity<Object> deleteSubscription(Map<String, String> header, String id) {
        return ResponseEntity.ok(subscriptionService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeleteSubscription(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(subscriptionService.bulkDelete(idList));
    }
}
