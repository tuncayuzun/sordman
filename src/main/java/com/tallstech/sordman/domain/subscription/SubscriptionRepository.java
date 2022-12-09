package com.tallstech.sordman.domain.subscription;

import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.subscription.document.Subscription;
import com.tallstech.sordman.exception.SordmanException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;


@Repository
public class SubscriptionRepository implements SordmanRepository<Subscription> {

    private MongoTemplate mongoTemplate;

    @Autowired
    public SubscriptionRepository(@Qualifier(SordmanConstants.MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Subscription findById(String id) {
        var subscription = mongoTemplate.findById(id, Subscription.class);
        if (ObjectUtils.isEmpty(subscription)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Subscription not found!");
        } else {
            return subscription;
        }
    }

    @Override
    public List<Subscription> getAll() {
        var subscriptionList = mongoTemplate.findAll(Subscription.class);
        if (ObjectUtils.isEmpty(subscriptionList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Subscription not found!");
        } else {
            return subscriptionList;
        }
    }

    @Override
    public Page<Subscription> getAllPaginated(int page, int size, String[] sortBy) {
        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<Subscription> subscriptionList = mongoTemplate.find(query, Subscription.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), Subscription.class);

            return new PageImpl<>(subscriptionList, pageable, subscriptionList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public Subscription save(Subscription subscription) {
        try {
            return mongoTemplate.save(subscription);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public Subscription update(Subscription subscription) {
        try {
            return mongoTemplate.save(subscription);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkUpdate(List<Subscription> subscriptionList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Subscription.class);
            for (Subscription subscription : subscriptionList) {
                var query = new Query().addCriteria(new Criteria("id").is(subscription.getId()));
                var update = new Update().set(field, value);
                bulkOperations.updateOne(query, update);
            }
            BulkWriteResult results = bulkOperations.execute();
            return results.getModifiedCount();
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            var subscription = findById(id);
            var deleteResult = mongoTemplate.remove(subscription);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Subscription.class);
            for (String id : idList) {
                var query = new Query().addCriteria(new Criteria("id").is(id));
                bulkOperations.remove(query);
            }
            BulkWriteResult results = bulkOperations.execute();
            return results.getDeletedCount();
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }
}
