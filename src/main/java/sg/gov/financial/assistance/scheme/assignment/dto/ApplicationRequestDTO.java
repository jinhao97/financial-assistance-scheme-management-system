package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationRequestDTO {

    @JsonProperty(value = "uin")
    private String uin;

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
