package com.tallstech.sordman.domain.base;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

@Data
public abstract class BaseDocument implements Persistable<String>{
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdOn;
    @LastModifiedDate
    private LocalDateTime updatedOn;
    @Version
    private Long version;
    public boolean isNew() {
        return ObjectUtils.isEmpty(createdOn);
    }
}
