package sg.gov.financial.assistance.scheme.assignment.entity;


import jakarta.persistence.*;
import sg.gov.financial.assistance.scheme.assignment.constant.DataType;

@Entity
@Table(name = "t_criteria_attributes", schema = "public")
public class CriteriaAttributesEntity extends AbstractAuditEntity {

    @Id
    @SequenceGenerator(name = "criteria_attributes_seq", sequenceName = "criteria_attributes_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_attributes_seq")
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @Column(name = "criteria_name")
    private String criteriaName;

    @Column(name = "criteria_type")
    @Enumerated(EnumType.STRING)
    private DataType criteriaType = DataType.STRING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public DataType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(DataType criteriaType) {
        this.criteriaType = criteriaType;
    }
}