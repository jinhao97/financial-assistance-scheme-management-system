package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "applicant")
    private ApplicantDTO applicant;

    @JsonProperty(value = "eligible_scheme")
    private SchemeDTO scheme;

    @JsonProperty(value = "eligible_benefits")
    private BenefitsDTO eligibleBenefits;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long id, ApplicantDTO applicant, SchemeDTO scheme) {
        this.id = id;
        this.applicant = applicant;
        this.scheme = scheme;
    }

    public ApplicationDTO(Long id, ApplicantDTO applicant, SchemeDTO scheme, BenefitsDTO eligibleBenefits) {
        this.id = id;
        this.applicant = applicant;
        this.scheme = scheme;
        this.eligibleBenefits = eligibleBenefits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    public SchemeDTO getScheme() {
        return scheme;
    }

    public void setScheme(SchemeDTO scheme) {
        this.scheme = scheme;
    }

    public BenefitsDTO getEligibleBenefits() {
        return eligibleBenefits;
    }

    public void setEligibleBenefits(BenefitsDTO eligibleBenefits) {
        this.eligibleBenefits = eligibleBenefits;
    }
}
