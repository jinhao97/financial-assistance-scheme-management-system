package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BenefitsDTO {

    @JsonProperty("benefits")
    private List<BenefitDetailsDTO> benefits;

    public BenefitsDTO() {
    }

    public BenefitsDTO(List<BenefitDetailsDTO> benefits) {
        this.benefits = benefits;
    }

    public List<BenefitDetailsDTO> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<BenefitDetailsDTO> benefits) {
        this.benefits = benefits;
    }
}
