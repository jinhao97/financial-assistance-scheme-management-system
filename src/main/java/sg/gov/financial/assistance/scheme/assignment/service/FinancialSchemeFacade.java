package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;

@Service
public class FinancialSchemeFacade {

    private final ApplicantService applicantService;
    private final ApplicationService applicationService;
    private final SchemeService schemeService;

    @Autowired
    public FinancialSchemeFacade(ApplicantService applicantService, ApplicationService applicationService, SchemeService schemeService) {
        this.applicantService = applicantService;
        this.applicationService = applicationService;
        this.schemeService = schemeService;
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
            var schemeEligibility = schemeService.determineEligibleBenefits(applicant, scheme, householdMembers);
            if (schemeEligibility.isPresent()) {
                return applicationService.saveApplication(applicant, scheme, schemeEligibility.get());
            }

        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        throw new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Applicant with UIN %s is not eligible for scheme %s", request.getUin(), request.getSchemeName()));
    }


}
