package sg.gov.financial.assistance.scheme.assignment.constant;

public enum EmploymentStatus {
    EMPLOYED("Employed"),
    UNEMPLOYED("Unemployed");

    private final String value;

    EmploymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
