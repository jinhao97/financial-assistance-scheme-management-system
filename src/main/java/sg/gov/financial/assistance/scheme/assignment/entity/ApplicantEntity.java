package sg.gov.financial.assistance.scheme.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import sg.gov.financial.assistance.scheme.assignment.constant.EmploymentStatus;
import sg.gov.financial.assistance.scheme.assignment.constant.Relationship;
import sg.gov.financial.assistance.scheme.assignment.constant.Sex;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_applicant", schema = "public")
public class ApplicantEntity extends AbstractAuditEntity {
    @Id
    @SequenceGenerator(name = "applicant_seq", sequenceName = "applicant_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applicant_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex = Sex.UNKNOWN;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "uin")
    private String uin;

    @Column(name = "employment_status")
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus = EmploymentStatus.UNEMPLOYED;

    @Column(name = "relationship")
    @Enumerated(EnumType.STRING)
    private Relationship relationship = Relationship.OTHERS;

    @ManyToOne
    @JoinColumn(name = "household_id")
    @JsonBackReference
    private ApplicantEntity household;

    // One applicant (head of the household) can have many household members
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ApplicantEntity> householdMembers;


    @OneToMany(mappedBy = "applicantEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ApplicationEntity> applicationList;

    public ApplicantEntity() {
    }

    public ApplicantEntity(String name, Relationship relationship, Sex sex, LocalDate dateOfBirth, String uin, EmploymentStatus employmentStatus) {
        this.name = name;
        this.relationship = relationship;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.uin = uin;
        this.employmentStatus = employmentStatus;
    }

    public ApplicantEntity getHousehold() {
        return household;
    }

    public void setHousehold(ApplicantEntity household) {
        this.household = household;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public List<ApplicantEntity> getHouseholdMembers() {
        return householdMembers;
    }

    public void setHouseholdMembers(List<ApplicantEntity> householdMembers) {
        this.householdMembers = householdMembers;
    }
}
