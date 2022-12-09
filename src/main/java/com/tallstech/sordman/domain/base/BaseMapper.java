package com.tallstech.sordman.domain.base;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

public interface BaseMapper<S, T>{

    T toDto(S s);

    @InheritInverseConfiguration
    S toDoc(T t);

    @InheritConfiguration
    List<T> toDtoList(List<S> sourceList);

    @InheritConfiguration
    List<S> toDocList(List<T> targetList);
}
