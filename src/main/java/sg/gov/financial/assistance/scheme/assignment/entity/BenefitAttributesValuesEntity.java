package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_benefit_attributes_values", schema = "public")
public class BenefitAttributesValuesEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "benefit_attributes_values_seq", sequenceName = "benefit_attributes_values_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_attributes_values_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @Column(name = "benefit_value")
    private String benefitValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "benefit_attributes_id", nullable = false)
    private BenefitAttributesEntity benefitAttributesEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id", nullable = false)
    private SchemeEntity schemeEntity;

    public BenefitAttributesValuesEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenefitValue() {
        return benefitValue;
    }

    public void setBenefitValue(String benefitValue) {
        this.benefitValue = benefitValue;
    }

    public BenefitAttributesEntity getBenefitAttributesEntity() {
        return benefitAttributesEntity;
    }

    public void setBenefitAttributesEntity(BenefitAttributesEntity benefitAttributesEntity) {
        this.benefitAttributesEntity = benefitAttributesEntity;
    }

    public SchemeEntity getSchemeEntity() {
        return schemeEntity;
    }

    public void setSchemeEntity(SchemeEntity schemeEntity) {
        this.schemeEntity = schemeEntity;
    }
}
