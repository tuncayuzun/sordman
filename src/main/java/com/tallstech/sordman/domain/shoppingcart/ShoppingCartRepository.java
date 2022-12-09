package com.tallstech.sordman.domain.shoppingcart;

import static com.tallstech.sordman.util.MongoUtils.generateSorts;

import java.util.List;

import com.mongodb.bulk.BulkWriteResult;
import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import com.tallstech.sordman.domain.shoppingcart.util.ShoppingCartUtils;
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
public class ShoppingCartRepository implements SordmanRepository<ShoppingCart> {

    private MongoTemplate mongoTemplate;


    @Autowired
    public ShoppingCartRepository(@Qualifier(SordmanConstants.MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ShoppingCart findById(String id) {
        var shoppingCart = mongoTemplate.findById(id, ShoppingCart.class);
        if (ObjectUtils.isEmpty(shoppingCart)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Shopping cart not found!");
        } else {
            return shoppingCart;
        }
    }

    public ShoppingCart findActiveShoppingCartByPartyId(Long partyId) {
        ShoppingCart shoppingCart;
        try {
            var query = new Query();
            query.addCriteria(Criteria.where("partyId").is(partyId).and("status").is("open"));
            shoppingCart = mongoTemplate.findOne(query, ShoppingCart.class);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> getAll() {
        var shoppingCartList = mongoTemplate.findAll(ShoppingCart.class);
        if (ObjectUtils.isEmpty(shoppingCartList)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Shopping carts not found!");
        } else {
            return shoppingCartList;
        }
    }

    @Override
    public Page<ShoppingCart> getAllPaginated(int page, int size, String[] sortBy) {
        try {
            var sorts = generateSorts(sortBy);

            final Pageable pageable = PageRequest.of(page, size, sorts);

            var query = new Query().
                    with(pageable)
                    .skip((long) page * size)
                    .limit(size);

            List<ShoppingCart> shoppingCartList = mongoTemplate.find(query, ShoppingCart.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), ShoppingCart.class);

            return new PageImpl<>(shoppingCartList, pageable, shoppingCartList.isEmpty() ? 0 : count);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        try {
            var existingShoppingCart = findActiveShoppingCartByPartyId(shoppingCart.getPartyId());
            if (ObjectUtils.isEmpty(existingShoppingCart)) {
                shoppingCart.setStatus(SordmanConstants.CART_STATUS_OPEN);
                shoppingCart.setValidityPeriod(ShoppingCartUtils.calculateValidityPeriod());
                return mongoTemplate.save(shoppingCart);
            } else {
                return existingShoppingCart;
            }
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        try {
            return mongoTemplate.save(shoppingCart);
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkUpdate(List<ShoppingCart> shoppingCartList, String field, Object value) throws SordmanException {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, ShoppingCart.class);
            for (ShoppingCart shoppingCart : shoppingCartList) {
                var query = new Query().addCriteria(new Criteria("id").is(shoppingCart.getId()));
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
            var shoppingCart = findById(id);
            var deleteResult = mongoTemplate.remove(shoppingCart);
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Override
    public int bulkDelete(List<String> idList) {
        try {
            var bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ShoppingCart.class);
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
