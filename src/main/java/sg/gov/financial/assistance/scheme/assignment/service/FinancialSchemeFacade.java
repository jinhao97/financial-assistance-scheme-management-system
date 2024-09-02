package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.EligibleSchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.mapper.ApplicantMapper;
import sg.gov.financial.assistance.scheme.assignment.service.scheme.SchemeService;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FinancialSchemeFacade {

    private final ApplicantService applicantService;
    private final ApplicationService applicationService;
    private final SchemeService schemeService;
    private final ApplicantMapper applicantMapper;

    @Autowired
    public FinancialSchemeFacade(ApplicantService applicantService, ApplicationService applicationService, SchemeService schemeService, ApplicantMapper applicantMapper) {
        this.applicantService = applicantService;
        this.applicationService = applicationService;
        this.schemeService = schemeService;
        this.applicantMapper = applicantMapper;
    }

    @Transactional
    public ApplicationDTO createApplication(ApplicationRequestDTO request) {
        try {
            var applicant = applicantService.findApplicant(request.getUin());
            var scheme = schemeService.getSchemeBySchemeName(request.getSchemeName());
            var existingApplication = applicationService.findExistingApplication(scheme.getId(), applicant.getId());

            if (existingApplication != null) {
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "Application already exists");
            }

            var householdMembers = applicantService.findHouseholdMembers(request.getUin());
            var schemeEligibility = schemeService.process(applicant, scheme, householdMembers);
            if (schemeEligibility != null) {
                return applicationService.saveApplication(applicant, scheme, schemeEligibility.getBenefits());
            } else {
                throw new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Applicant with UIN %s is not eligible for scheme %s", request.getUin(), request.getSchemeName()));
            }

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Transactional
    public EligibleSchemeDTO showApplicantEligibleSchemes(String uin) {

        try {
            var applicant = applicantService.findApplicant(uin);
            var schemes = schemeService.getSchemes();
            var householdMembers = applicantService.findHouseholdMembers(uin);

            var eligibleSchemes = schemes.stream()
                    .map(s -> schemeService.process(applicant, s, householdMembers))
                    .filter(Objects::nonNull)
                    .map(SchemeDTO::getName)
                    .collect(Collectors.toList());

            return new EligibleSchemeDTO(applicantMapper.toDTO(applicant), eligibleSchemes);

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


}
