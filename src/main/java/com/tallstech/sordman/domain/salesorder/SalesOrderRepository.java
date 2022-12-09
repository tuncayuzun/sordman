package com.tallstech.sordman.domain.salesorder;

import static com.tallstech.sordman.constant.SordmanConstants.MONGO_TEMPLATE;
import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
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
public class SalesOrderRepository implements SordmanRepository<SalesOrder> {

    private MongoTemplate mongoTemplate;


    @Autowired
    public SalesOrderRepository(@Qualifier(MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public SalesOrder findById(String id) {
        var salesOrder = mongoTemplate.findById(id, SalesOrder.class);
        if (ObjectUtils.isEmpty(salesOrder)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Order not found!");
        } else {
            return salesOrder;
        }
    }

    @Override
    public List<SalesOrder> getAll() {
        var salesOrderList = mongoTemplate.findAll(SalesOrder.class);
        if (ObjectUtils.isEmpty(salesOrderList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Orders not found!");
        } else {
            return salesOrderList;
        }
    }

    @Override
    public Page<SalesOrder> getAllPaginated(int page, int size, String[] sortBy) {
        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<SalesOrder> salesOrderList = mongoTemplate.find(query, SalesOrder.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), SalesOrder.class);

            return new PageImpl<>(salesOrderList, pageable, salesOrderList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public SalesOrder save(SalesOrder salesOrder) {
        try {
            return mongoTemplate.save(salesOrder);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public SalesOrder update(SalesOrder salesOrder) {
        try {
            return mongoTemplate.save(salesOrder);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkUpdate(List<SalesOrder> salesOrderList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, SalesOrder.class);
            for (SalesOrder salesOrder : salesOrderList) {
                var query = new Query().addCriteria(new Criteria("id").is(salesOrder.getId()));
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
            var salesOrder = findById(id);
            var deleteResult = mongoTemplate.remove(salesOrder);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, SalesOrder.class);
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
