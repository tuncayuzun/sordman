package com.tallstech.sordman.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


public final class MongoUtils {

    private MongoUtils() {
    }

    public static <T, S> Map<String, T> generatePageMap(List<T> objectResultList, Page<S> page) {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("result", objectResultList);
        pageMap.put("page", page.getPageable());
        return (Map<String, T>) pageMap;
    }

    public static Sort generateSorts(String[] sortBy) {
        return Sort.by(
                Arrays.stream(sortBy)
                        .map(sort -> sort.split(";", 2))
                        .map(array ->
                                new Sort.Order(generateDirection(array[1]), array[0])
                        ).collect(Collectors.toList())
        );
    }

    private static Sort.Direction generateDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("DESC")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }
}
