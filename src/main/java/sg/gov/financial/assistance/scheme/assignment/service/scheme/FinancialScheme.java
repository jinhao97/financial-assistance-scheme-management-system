package sg.gov.financial.assistance.scheme.assignment.service.scheme;

import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;

import java.util.List;

public interface FinancialScheme {

    SchemeDTO checkEligibility(ApplicantEntity applicant, SchemeEntity scheme, List<HouseholdData> householdMembers);


}
