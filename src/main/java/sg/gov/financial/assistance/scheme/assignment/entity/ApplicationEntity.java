package sg.gov.financial.assistance.scheme.assignment.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sg.gov.financial.assistance.scheme.assignment.constant.ApplicationStatus;


import java.time.LocalDateTime;

@Entity
@Table(name = "t_applications", schema = "public")
@Convert(attributeName = "jsonb", converter = JsonBinaryType.class)
public class ApplicationEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "applications_seq", sequenceName = "applications_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applications_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id", nullable = false)
    private ApplicantEntity applicantEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id", nullable = false)
    private SchemeEntity schemeEntity;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "benefit_details")
    private String benefitDetails;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "submissionDateTime")
    private LocalDateTime submissionDateTime;

    public ApplicationEntity() {
    }

    public ApplicationEntity(ApplicantEntity applicantEntity, SchemeEntity schemeEntity, String benefitDetails, LocalDateTime submissionDateTime, ApplicationStatus status) {
        this.applicantEntity = applicantEntity;
        this.schemeEntity = schemeEntity;
        this.benefitDetails = benefitDetails;
        this.submissionDateTime = submissionDateTime;
        this.status = status;
    }

    public ApplicantEntity getApplicantEntity() {
        return applicantEntity;
    }

    public void setApplicantEntity(ApplicantEntity applicantEntity) {
        this.applicantEntity = applicantEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchemeEntity getSchemeEntity() {
        return schemeEntity;
    }

    public void setSchemeEntity(SchemeEntity schemeEntity) {
        this.schemeEntity = schemeEntity;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(LocalDateTime submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public String getBenefitDetails() {
        return benefitDetails;
    }

    public void setBenefitDetails(String benefitDetails) {
        this.benefitDetails = benefitDetails;
    }
}
