package com.tallstech.sordman.domain.discount;

import static com.tallstech.sordman.constant.SordmanConstants.MONGO_TEMPLATE;
import static com.tallstech.sordman.exception.ErrorMessages.ITEM_NOT_FOUND;
import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.domain.discount.document.Discount;
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
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.exception.SordmanException;


@Repository
public class DiscountRepository implements SordmanRepository<Discount> {

    private MongoTemplate mongoTemplate;


    @Autowired
    public DiscountRepository(@Qualifier(MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Discount findById(String id) {
        var discount = mongoTemplate.findById(id, Discount.class);
        if (ObjectUtils.isEmpty(discount)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, ITEM_NOT_FOUND);
        } else {
            return discount;
        }
    }

    public Discount findByCode(String code) {
        var query = new Query().addCriteria(new Criteria("code").is(code));
        var discount = mongoTemplate.findOne(query, Discount.class);
        if (ObjectUtils.isEmpty(discount)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, ITEM_NOT_FOUND);
        } else {
            return discount;
        }
    }


    @Override
    public List<Discount> getAll() {
        var discountList = mongoTemplate.findAll(Discount.class);
        if (ObjectUtils.isEmpty(discountList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, ITEM_NOT_FOUND);
        } else {
            return discountList;
        }
    }

    @Override
    public Page<Discount> getAllPaginated(int page, int size, String[] sortBy) {

        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<Discount> discountList = mongoTemplate.find(query, Discount.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), Discount.class);

            return new PageImpl<>(discountList, pageable, discountList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_FOUND, exception);
        }

    }

    @Override
    public Discount save(Discount discount) {
        try {
            return mongoTemplate.save(discount);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public Discount update(Discount discount) {
        try {
            return mongoTemplate.save(discount);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public int bulkUpdate(List<Discount> discountList, String field, Object value) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Discount.class);
            for (Discount discount : discountList) {
                var query = new Query().addCriteria(new Criteria("id").is(discount.getId()));
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
            var discount = findById(id);
            var deleteResult = mongoTemplate.remove(discount);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_MODIFIED, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Discount.class);
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
