package sg.gov.financial.assistance.scheme.assignment.constant;

public enum Relationship {
    FATHER("Father"),
    MOTHER("Mother"),
    SON("Son"),
    DAUGHTER("Daughter"),
    SPOUSE("Spouse"),
    BROTHER("Brother"),
    SISTER("Sister"),
    GRANDFATHER("Grandfather"),
    GRANDMOTHER("Grandmother"),
    IN_LAW("In-Law"),
    OTHERS("Others");

    private final String value;

    Relationship(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
