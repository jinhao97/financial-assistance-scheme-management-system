package sg.gov.financial.assistance.scheme.assignment.dto;

public class LoginDTO {
    private String uin;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String uin, String password) {
        this.uin = uin;
        this.password = password;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
