package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "t_schemes", schema = "public")
public class SchemeEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "scheme_seq", sequenceName = "scheme_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheme_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = "description")
    private String description;

    @Column(name = "displayName")
    private String displayName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "schemeEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CriteriaAttributesValuesEntity> criteriaAttributesValues;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<CriteriaAttributesValuesEntity> getCriteriaAttributesValues() {
        return criteriaAttributesValues;
    }

    public void setCriteriaAttributesValues(Set<CriteriaAttributesValuesEntity> criteriaAttributesValues) {
        this.criteriaAttributesValues = criteriaAttributesValues;
    }
}
