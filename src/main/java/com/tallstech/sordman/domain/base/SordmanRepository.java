package com.tallstech.sordman.domain.base;

import java.util.List;

import com.tallstech.sordman.exception.SordmanException;
import org.springframework.data.domain.Page;


public interface SordmanRepository<T> {
    List<T> getAll();

    Page<T> getAllPaginated(int page, int size, String[] sortBy);

    T save(T t);

    T update(T t);

    int bulkUpdate(List<T> list, String field, Object value) throws SordmanException;

    T findById(String id);

    boolean delete(String id);
    int bulkDelete(List<String> idList);
}
