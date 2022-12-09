package com.tallstech.sordman.domain.base;

import java.util.List;

import com.tallstech.sordman.exception.SordmanException;
import org.springframework.data.domain.Page;


public interface SordmanService<T> {

    T getById(String id);

    List<T> getAll();

    Page<T> getAllPaginated(int page, int size, String[] sortBy);

    T create(T t);

    T update(T t);

    int bulkUpdate(List<T> tList, String field, Object value) throws SordmanException;

    boolean delete(String id);

    int bulkDelete(List<String> idList) throws SordmanException;
}
