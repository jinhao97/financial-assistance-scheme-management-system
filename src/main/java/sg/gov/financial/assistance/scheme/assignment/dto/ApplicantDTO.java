package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.gov.financial.assistance.scheme.assignment.constant.EmploymentStatus;
import sg.gov.financial.assistance.scheme.assignment.constant.MaritialStatus;
import sg.gov.financial.assistance.scheme.assignment.constant.Relationship;
import sg.gov.financial.assistance.scheme.assignment.constant.Sex;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "sex")
    private Sex sex;

    @JsonProperty(value = "dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "uin")
    private String uin;

    @JsonProperty(value = "employmentStatus")
    private EmploymentStatus employmentStatus;

    @JsonProperty(value = "maritialStatus")
    private MaritialStatus maritialStatus;

    @JsonProperty(value = "relationship")
    private Relationship relationship;

    @JsonProperty(value = "householdMembers")
    private List<ApplicantDTO> householdMembers;

    public ApplicantDTO() {
    }

    public ApplicantDTO(Long id, String name, Sex sex, LocalDate dateOfBirth, String uin, EmploymentStatus employmentStatus, MaritialStatus maritialStatus,Relationship relationship) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.uin = uin;
        this.employmentStatus = employmentStatus;
        this.maritialStatus = maritialStatus;
        this.relationship = relationship;
    }

    public ApplicantDTO(Long id, String name, Sex sex, LocalDate dateOfBirth, String uin, EmploymentStatus employmentStatus, Relationship relationship, MaritialStatus maritialStatus,List<ApplicantDTO> householdMembers) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.uin = uin;
        this.employmentStatus = employmentStatus;
        this.maritialStatus = maritialStatus;
        this.relationship = relationship;
        this.householdMembers = householdMembers;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
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

    public List<ApplicantDTO> getHouseholdMembers() {
        return householdMembers;
    }

    public void setHouseholdMembers(List<ApplicantDTO> householdMembers) {
        this.householdMembers = householdMembers;
    }

    public MaritialStatus getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(MaritialStatus maritialStatus) {
        this.maritialStatus = maritialStatus;
    }
}
