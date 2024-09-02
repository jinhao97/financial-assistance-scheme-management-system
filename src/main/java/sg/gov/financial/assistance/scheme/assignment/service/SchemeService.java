package sg.gov.financial.assistance.scheme.assignment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.constant.EmploymentStatus;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.mapper.SchemeMapper;
import sg.gov.financial.assistance.scheme.assignment.repository.SchemeRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;
    private final SchemeMapper schemeMapper;

    private static final String SKILLS_FUTURE_CREDITS = "SkillsFuture Credits";
    private static final String SCHOOL_MEAL_VOUCHER = "School Meal Voucher";
    private static final String CRITERIA_HAS_CHILDREN = "has_children";
    private static final String CRITERIA_SCHOOL_LEVEL = "school_level";
    private static final String SCHOOL_LEVEL_PRIMARY = "primary";

    @Autowired
    public SchemeService(SchemeRepository schemeRepository, SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
    }

    public List<SchemeDTO> getAllAvailableSchemes() {
        List<SchemeEntity> schemes = schemeRepository.findAllSchemesWithinCurrentDate();
        return schemes.stream()
                .map(schemeEntity -> schemeMapper.toDTO(schemeEntity, true))
                .collect(Collectors.toList());
    }

    public SchemeEntity getSchemeBySchemeName(String schemeName) {
        return Optional.ofNullable(schemeRepository.findBySchemeName(schemeName))
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Invalid scheme %s", schemeName)));
    }

    public Optional<Map<String, String>> determineEligibleBenefits(ApplicantEntity applicant, SchemeEntity scheme, List<HouseholdData> householdMembers) {
        SchemeDTO schemeDTO = schemeMapper.toDTO(scheme, true);

        if (isCriteriaMet(schemeDTO.getCriteria(), applicant.getEmploymentStatus())) {
            return Optional.of(calculateEligibleBenefits(householdMembers, schemeDTO.getCriteria(), schemeDTO.getBenefits()));
        }

        return Optional.empty();
    }


    private boolean isCriteriaMet(Map<String, String> criteria, EmploymentStatus applicantEmploymentStatus) {
        var employmentStatus = criteria.get("employment_status");
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