package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EligibleSchemeDTO {

    private ApplicantDTO applicantDTO;

    private List<String> schemes;

    public EligibleSchemeDTO(ApplicantDTO applicantDTO, List<String> schemes) {
        this.applicantDTO = applicantDTO;
        this.schemes = schemes;
    }

    public ApplicantDTO getApplicantDTO() {
        return applicantDTO;
    }

    public void setApplicantDTO(ApplicantDTO applicantDTO) {
        this.applicantDTO = applicantDTO;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }
}
