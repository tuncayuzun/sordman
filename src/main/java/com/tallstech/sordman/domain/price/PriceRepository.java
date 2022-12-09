package com.tallstech.sordman.domain.price;

import static com.tallstech.sordman.constant.SordmanConstants.MONGO_TEMPLATE;
import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.price.document.Price;
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
public class PriceRepository implements SordmanRepository<Price> {

    private MongoTemplate mongoTemplate;


    @Autowired
    public PriceRepository(@Qualifier(MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Price findById(String id) {
        var price = mongoTemplate.findById(id, Price.class);
        if (ObjectUtils.isEmpty(price)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Price not found!");
        } else {
            return price;
        }
    }


    @Override
    public List<Price> getAll() {
        var priceLit = mongoTemplate.findAll(Price.class);
        if (ObjectUtils.isEmpty(priceLit)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Prices not found!");
        } else {
            return priceLit;
        }
    }

    @Override
    public Page<Price> getAllPaginated(int page, int size, String[] sortBy) {

        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<Price> priceList = mongoTemplate.find(query, Price.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), Price.class);

            return new PageImpl<>(priceList, pageable, priceList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.NOT_FOUND, exception);
        }

    }

    @Override
    public Price save(Price priceToSave) {
        try {
            return mongoTemplate.save(priceToSave);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public Price update(Price priceToUpdate) {
        try {
            return mongoTemplate.save(priceToUpdate);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkUpdate(List<Price> priceList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Price.class);
            for (Price price : priceList) {
                var query = new Query().addCriteria(new Criteria("id").is(price.getId()));
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
            var price = findById(id);
            var deleteResult = mongoTemplate.remove(price);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Price.class);
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
