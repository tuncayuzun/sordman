package com.tallstech.sordman.domain.payment;

import static com.tallstech.sordman.constant.SordmanConstants.MONGO_TEMPLATE;
import static com.tallstech.sordman.exception.ErrorMessages.ITEM_NOT_FOUND;
import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.payment.document.Payment;
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
public class PaymentRepository implements SordmanRepository<Payment> {

    private MongoTemplate mongoTemplate;


    @Autowired
    public PaymentRepository(@Qualifier(MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Payment findById(String id) {
        var payment = mongoTemplate.findById(id, Payment.class);
        if (ObjectUtils.isEmpty(payment)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, ITEM_NOT_FOUND);
        } else {
            return payment;
        }
    }


    @Override
    public List<Payment> getAll() {
        var paymentList = mongoTemplate.findAll(Payment.class);
        if (ObjectUtils.isEmpty(paymentList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, ITEM_NOT_FOUND);
        } else {
            return paymentList;
        }
    }

    @Override
    public Page<Payment> getAllPaginated(int page, int size, String[] sortBy) {

        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<Payment> paymentList = mongoTemplate.find(query, Payment.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), Payment.class);

            return new PageImpl<>(paymentList, pageable, paymentList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_FOUND, exception);
        }

    }

    @Override
    public Payment save(Payment payment) {
        try {
            return mongoTemplate.save(payment);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_FOUND, exception);
        }
    }

    @Override
    public Payment update(Payment payment) {
        try {
            return mongoTemplate.save(payment);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public int bulkUpdate(List<Payment> paymentList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Payment.class);
            for (Payment payment : paymentList) {
                var query = new Query().addCriteria(new Criteria("id").is(payment.getId()));
                var update = new Update().set(field, value);
                bulkOperations.updateOne(query, update);
            }
            BulkWriteResult results = bulkOperations.execute();
            return results.getModifiedCount();
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            var payment = findById(id);
            var deleteResult = mongoTemplate.remove(payment);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Payment.class);
            for (String id : idList) {
                var query = new Query().addCriteria(new Criteria("id").is(id));
                bulkOperations.remove(query);
            }
            BulkWriteResult results = bulkOperations.execute();
            return results.getDeletedCount();
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

}
