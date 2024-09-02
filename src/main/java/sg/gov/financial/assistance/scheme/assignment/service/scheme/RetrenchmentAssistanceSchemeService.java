package sg.gov.financial.assistance.scheme.assignment.service.scheme;

import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.constant.EmploymentStatus;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.mapper.SchemeMapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sg.gov.financial.assistance.scheme.assignment.service.scheme.SchemeType.REA_SCH;


@Service(value = REA_SCH)
public class RetrenchmentAssistanceSchemeService implements FinancialScheme {

    private static final String SKILLS_FUTURE_CREDITS = "SkillsFuture Credits";
    private static final String SCHOOL_MEAL_VOUCHER = "School Meal Voucher";
    private static final String CRITERIA_HAS_CHILDREN = "has_children";
    private static final String CRITERIA_SCHOOL_LEVEL = "school_level";
    private static final String CRITERIA_EMPLOYMENT_STATUS = "employment_status";
    private static final String SCHOOL_LEVEL_PRIMARY = "primary";

    private final SchemeMapper schemeMapper;

    public RetrenchmentAssistanceSchemeService(SchemeMapper schemeMapper) {
        this.schemeMapper = schemeMapper;
    }

    @Override
    public SchemeDTO checkEligibility(ApplicantEntity applicant, SchemeEntity scheme, List<HouseholdData> householdMembers) {

        var schemeDTO = schemeMapper.toDTO(scheme, true);

        if (isCriteriaMet(schemeDTO.getCriteria(), applicant.getEmploymentStatus())) {
            var eligibleBenefits = calculateEligibleBenefits(householdMembers, schemeDTO.getCriteria(), schemeDTO.getBenefits());
            return new SchemeDTO(
                    scheme.getId(),
                    scheme.getSchemeName(),
                    scheme.getDescription(),
                    scheme.getDisplayName(),
                    scheme.getStartDate(),
                    scheme.getEndDate(),
                    null,
                    eligibleBenefits);
        }

        return null;
    }

    private boolean isCriteriaMet(Map<String, String> criteria, EmploymentStatus applicantEmploymentStatus) {
        var employmentStatus = criteria.get(CRITERIA_EMPLOYMENT_STATUS);
        return employmentStatus.equals(applicantEmploymentStatus.getValue());
    }

    private Map<String, String> calculateEligibleBenefits(List<HouseholdData> householdMembers, Map<String, String> criteria, Map<String, String> benefitMap) {

        Map<String, String> eligibleBenefitsMap = new HashMap<>();

        addSkillsFutureCredits(benefitMap, eligibleBenefitsMap);
        checkSchoolMealVoucherEligibility(householdMembers, criteria, benefitMap, eligibleBenefitsMap);

        return eligibleBenefitsMap;
    }

    private void addSkillsFutureCredits(Map<String, String> benefitMap, Map<String, String> eligibleBenefitsMap) {
        if (benefitMap.containsKey(SKILLS_FUTURE_CREDITS)) {
            eligibleBenefitsMap.put(SKILLS_FUTURE_CREDITS, benefitMap.get(SKILLS_FUTURE_CREDITS));
        }
    }

    private void checkSchoolMealVoucherEligibility(List<HouseholdData> householdMembers, Map<String, String> criteria, Map<String, String> benefitMap, Map<String, String> eligibleBenefitsMap) {
        boolean hasChildren = Boolean.parseBoolean(criteria.get(CRITERIA_HAS_CHILDREN));
        boolean isPrimarySchool = SCHOOL_LEVEL_PRIMARY.equals(criteria.get(CRITERIA_SCHOOL_LEVEL));

        if (!householdMembers.isEmpty() && hasChildren && isPrimarySchool) {
            boolean hasPrimaryChildMember = householdMembers.stream()
                    .anyMatch(this::isPrimarySchoolChild);

            if (hasPrimaryChildMember) {
                eligibleBenefitsMap.put(SCHOOL_MEAL_VOUCHER, benefitMap.get(SCHOOL_MEAL_VOUCHER));
            }
        }
    }

    private boolean isPrimarySchoolChild(HouseholdData member) {
        int age = Period.between(member.getApplicant().getDateOfBirth(), LocalDate.now()).getYears();
        return age >= 6 && age <= 12;
    }

}
