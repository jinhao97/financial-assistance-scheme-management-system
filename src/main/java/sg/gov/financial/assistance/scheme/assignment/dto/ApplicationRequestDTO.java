package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationRequestDTO {

    @Schema(example = "S6336574B")
    @JsonProperty(value = "uin")
    private String uin;

    @Schema(example = "REA_SCH")
    @JsonProperty(value = "schemeName")
    private String schemeName;

    public ApplicationRequestDTO() {}

    public ApplicationRequestDTO(String uin, String schemeName) {
        this.uin = uin;
        this.schemeName = schemeName;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
