package sg.gov.financial.assistance.scheme.assignment.constant;

public enum ApplicationStatus {

    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
