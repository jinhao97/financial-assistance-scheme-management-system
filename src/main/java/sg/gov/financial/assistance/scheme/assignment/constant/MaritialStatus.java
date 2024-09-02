package sg.gov.financial.assistance.scheme.assignment.constant;

public enum MaritialStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    WIDOWED("Widowed"),
    DIVORCED("Divorced");


    private final String value;

    MaritialStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
