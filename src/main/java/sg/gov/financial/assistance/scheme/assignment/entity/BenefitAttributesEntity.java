package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;
import sg.gov.financial.assistance.scheme.assignment.constant.DataType;

@Entity
@Table(name = "t_benefit_attributes", schema = "public")
public class BenefitAttributesEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "benefit_attributes_seq", sequenceName = "benefit_attributes_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_attributes_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @Column(name = "benefit_name")
    private String benefitName;

    @Column(name = "benefit_type")
    @Enumerated(EnumType.STRING)
    private DataType benefitType = DataType.STRING;

    public BenefitAttributesEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setBenefitName(String benefitName) {
        this.benefitName = benefitName;
    }

    public DataType getBenefitType() {
        return benefitType;
    }

    public void setBenefitType(DataType benefitType) {
        this.benefitType = benefitType;
    }
}
