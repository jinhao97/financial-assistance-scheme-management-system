package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequestDTO {

    private String uin;

    private String password;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String uin, String password) {
        this.uin = uin;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }
}
