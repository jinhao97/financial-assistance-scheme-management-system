package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_criteria_attributes_values", schema = "public")
public class CriteriaAttributesValuesEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "criteria_attributes_values_seq", sequenceName = "criteria_attributes_values_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_attributes_values_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @Column(name = "criteria_value")
    private String criteriaValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criteria_attributes_id", nullable = false)
    private CriteriaAttributesEntity criteriaAttributesEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id", nullable = false)
    private SchemeEntity schemeEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(String criteriaValue) {
        this.criteriaValue = criteriaValue;
    }

    public CriteriaAttributesEntity getCriteriaAttributesEntity() {
        return criteriaAttributesEntity;
    }

    public void setCriteriaAttributesEntity(CriteriaAttributesEntity criteriaAttributesEntity) {
        this.criteriaAttributesEntity = criteriaAttributesEntity;
    }

    public SchemeEntity getSchemeEntity() {
        return schemeEntity;
    }

    public void setSchemeEntity(SchemeEntity schemeEntity) {
        this.schemeEntity = schemeEntity;
    }
}
