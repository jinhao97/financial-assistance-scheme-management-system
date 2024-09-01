package sg.gov.financial.assistance.scheme.assignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.constant.ApplicationStatus;
import sg.gov.financial.assistance.scheme.assignment.constant.EmploymentStatus;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.BenefitDetailsDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.BenefitsDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final SchemeService schemeService;
    private final ApplicantService applicantService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, SchemeService schemeService, ApplicantService applicantService, ObjectMapper objectMapper) {
        this.applicationRepository = applicationRepository;
        this.schemeService = schemeService;
        this.applicantService = applicantService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public List<ApplicationDTO> fetchAllApplications() {
        try {
            List<ApplicationEntity> applications = applicationRepository.findAll();
            return applications.stream()
                    .map(this::convertToApplicationDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public List<ApplicationDTO> findApplicationsByApplicant(String uin) {
        try {
            var applicant = applicantService.findApplicant(uin);

            if (applicant != null) {
                var applications = applicationRepository.findApplicationEntitiesByApplicantEntityId(applicant.getId());
                return applications.stream()
                        .map(this::convertToApplicationDTO)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return null;
    }

    @Transactional
    public ApplicationDTO createApplication(ApplicationRequestDTO request) {
        try {
            var applicant = applicantService.findApplicant(request.getUin());
            var scheme = schemeService.getSchemeBySchemeName(request.getSchemeName());
            var application = applicationRepository.findApplicationEntitiesBySchemeEntityIdAndApplicantEntityId(scheme.getId(), applicant.getId());

            if (application != null) {
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "Application already exists");
            }

            var householdMembers = applicantService.findHouseholdMembers(request.getUin());
            var schemeDTO = schemeService.convertToSchemeDTO(scheme);
            boolean isCriteriaMet = isCriteriaMet(schemeDTO.getCriteria());

            if (isCriteriaMet) {
                var eligibleBenefits = findEligibleBenefits(householdMembers, schemeDTO.getCriteria(), schemeDTO.getBenefits());
                List<BenefitDetailsDTO> benefitDetails = eligibleBenefits.entrySet().stream()
                        .map(entry -> new BenefitDetailsDTO(entry.getKey(), parseAmount(entry.getValue())))
                        .collect(Collectors.toList());

                var benefitsDTO = new BenefitsDTO(benefitDetails);

                var newApplication = new ApplicationEntity(applicant, scheme, objectMapper.writeValueAsString(benefitsDTO), LocalDateTime.now(), ApplicationStatus.PENDING);
                var applicationEntity = applicationRepository.save(newApplication);
                return convertToApplicationDTO(applicationEntity);

            }
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        throw new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Applicant with UIN %s is not eligible for scheme %s", request.getUin(), request.getSchemeName()));
    }

    private Boolean isCriteriaMet(Map<String, String> criteria) {
        var employmentStatus = criteria.get("employment_status");
        return employmentStatus.equals(EmploymentStatus.UNEMPLOYED.getValue());
    }

    private Map<String, String> findEligibleBenefits(List<HouseholdData> householdMembers, Map<String, String> criteria, Map<String, String> benefitMap) {

        Map<String, String> eligibleBenefits = new HashMap<>();

        if (benefitMap.containsKey("SkillsFuture Credits")) {
            eligibleBenefits.put("SkillsFuture Credits", benefitMap.get("SkillsFuture Credits"));
        }

        boolean hasChildren = criteria.get("has_children").equals("true");
        boolean isPrimarySchool = criteria.get("school_level").equals("primary");

        if (!householdMembers.isEmpty() && hasChildren && isPrimarySchool) {
            var hasPrimaryChildMember = householdMembers.stream()
                    .anyMatch(member -> {
                        int age = Period.between(member.getApplicant().getDateOfBirth(), LocalDate.now()).getYears();
                        return age >= 6 && age <= 12;
                    });
            if (hasPrimaryChildMember) {
                eligibleBenefits.put("School Meal Voucher", benefitMap.get("School Meal Voucher"));
                return eligibleBenefits;
            }

        }

        return eligibleBenefits;

    }

    private ApplicationDTO convertToApplicationDTO(ApplicationEntity applicationEntity) {
        try {
            var applicationDTO = applicantService.convertToApplicantDTO(applicationEntity.getApplicantEntity());
            var schemeDTO = schemeService.schemeDetails(applicationEntity.getSchemeEntity());
            var benefits = objectMapper.readValue(applicationEntity.getBenefitDetails(), BenefitsDTO.class);
            return new ApplicationDTO(applicationEntity.getId(), applicationDTO, schemeDTO, benefits);
        } catch (Exception ex) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private BigDecimal parseAmount(String amountStr) {
        try {
            return new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

}
