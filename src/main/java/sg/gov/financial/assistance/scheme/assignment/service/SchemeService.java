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
        if (benefitMap.containsKey("SkillsFuture Credits")) {
            eligibleBenefitsMap.put("SkillsFuture Credits", benefitMap.get("SkillsFuture Credits"));
        }
    }

    private void checkSchoolMealVoucherEligibility(List<HouseholdData> householdMembers, Map<String, String> criteria, Map<String, String> benefitMap, Map<String, String> eligibleBenefitsMap) {
        boolean hasChildren = Boolean.parseBoolean(criteria.get("has_children"));
        boolean isPrimarySchool = "primary".equals(criteria.get("school_level"));

        if (!householdMembers.isEmpty() && hasChildren && isPrimarySchool) {
            boolean hasPrimaryChildMember = householdMembers.stream()
                    .anyMatch(this::isPrimarySchoolChild);

            if (hasPrimaryChildMember) {
                eligibleBenefitsMap.put("School Meal Voucher", benefitMap.get("School Meal Voucher"));
            }
        }
    }

    private boolean isPrimarySchoolChild(HouseholdData member) {
        int age = Period.between(member.getApplicant().getDateOfBirth(), LocalDate.now()).getYears();
        return age >= 6 && age <= 12;
    }


}