package sg.gov.financial.assistance.scheme.assignment.entity;


public class HouseholdData {

    private ApplicantEntity applicant;

    public HouseholdData(ApplicantEntity applicant) {
        this.applicant = applicant;
    }

    public ApplicantEntity getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantEntity applicant) {
        this.applicant = applicant;
    }


}
