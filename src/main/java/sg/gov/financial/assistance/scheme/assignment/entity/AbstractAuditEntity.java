package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity {

    @NotNull
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy = "SYSTEM";

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @NotNull
    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy = "SYSTEM";

    @NotNull
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Version
    private Long version;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;




}
