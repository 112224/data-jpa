package study.datajpa.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTimeEntity {

    @JsonIgnore @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @JsonIgnore @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime lastModifiedDate;
}
