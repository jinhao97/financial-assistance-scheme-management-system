package sg.gov.financial.assistance.scheme.assignment.constant;

public enum Sex {

    MALE("M"),
    FEMALE("F"),
    UNKNOWN("U");

    private final String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
