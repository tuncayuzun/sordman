package com.tallstech.sordman.domain.productcatalog;

import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.productcatalog.document.ProductCatalog;
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
public class ProductCatalogRepository implements SordmanRepository<ProductCatalog> {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProductCatalogRepository(@Qualifier(SordmanConstants.MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ProductCatalog findById(String id) {
        var productCatalog = mongoTemplate.findById(id, ProductCatalog.class);
        if (ObjectUtils.isEmpty(productCatalog)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Product catalog not found!");
        } else {
            return productCatalog;
        }
    }

    @Override
    public List<ProductCatalog> getAll() {
        var query = new Query();
        query.addCriteria(Criteria.where("status").is("active"));

        var productCatalogList = mongoTemplate.find(query, ProductCatalog.class);
        if (ObjectUtils.isEmpty(productCatalogList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Product catalogs not found!");
        } else {
            return productCatalogList;
        }
    }

    @Override
    public Page<ProductCatalog> getAllPaginated(int page, int size, String[] sortBy) {
        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<ProductCatalog> productCatalogList = mongoTemplate.find(query, ProductCatalog.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), ProductCatalog.class);

            return new PageImpl<>(productCatalogList, pageable, productCatalogList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public ProductCatalog save(ProductCatalog productCatalog) {
        try {
            return mongoTemplate.save(productCatalog);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public ProductCatalog update(ProductCatalog productCatalog) {
        try {
            return mongoTemplate.save(productCatalog);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkUpdate(List<ProductCatalog> productCatalogList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, ProductCatalog.class);
            for (ProductCatalog productCatalog : productCatalogList) {
                var query = new Query().addCriteria(new Criteria("id").is(productCatalog.getId()));
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
            var productCatalog = findById(id);
            var deleteResult = mongoTemplate.remove(productCatalog);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ProductCatalog.class);
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
