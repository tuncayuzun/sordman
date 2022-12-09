package com.tallstech.sordman.domain.base;

import java.util.List;

import com.tallstech.sordman.exception.SordmanException;
import org.springframework.data.domain.Page;


public abstract class SordmanServiceImpl<T> implements SordmanService<T> {

    private SordmanRepository<T> sordmanRepository;

    protected SordmanServiceImpl(SordmanRepository<T> sordmanRepository) {
        this.sordmanRepository = sordmanRepository;
    }

    public T getById(String id) {
        return sordmanRepository.findById(id);
    }

    public List<T> getAll() {
        return sordmanRepository.getAll();
    }

    public Page<T> getAllPaginated(int page, int size, String[] sortBy) {
        return sordmanRepository.getAllPaginated(page, size, sortBy);
    }

    public T create(T t) {
        return sordmanRepository.save(t);
    }

    public T update(T t) {
        return sordmanRepository.update(t);
    }

    public int bulkUpdate(List<T> tList, String field, Object value) throws SordmanException {
        return sordmanRepository.bulkUpdate(tList, field, value);
    }

    public boolean delete(String id) {
        return sordmanRepository.delete(id);
    }

    public int bulkDelete(List<String> idList) throws SordmanException {
        return sordmanRepository.bulkDelete(idList);
    }

}
