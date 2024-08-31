package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final SchemeService schemeService;
    private final ApplicantService applicantService;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, SchemeService schemeService, ApplicantService applicantService) {
        this.applicationRepository = applicationRepository;
        this.schemeService = schemeService;
        this.applicantService = applicantService;
    }

    @Transactional
    public List<ApplicationDTO> fetchAllApplications() {
        List<ApplicationEntity> applications = applicationRepository.findAll();
        return applications.stream()
                .map(this::convertToApplicationDTO)
                .collect(Collectors.toList());

    }

    private ApplicationDTO convertToApplicationDTO(ApplicationEntity applicationEntity) {
        var applicationDTO = applicantService.convertToApplicantDTO(applicationEntity.getApplicantEntity());
        var schemeDTO = schemeService.convertToSchemeDTO(applicationEntity.getSchemeEntity());
        return new ApplicationDTO(applicationEntity.getId(), applicationDTO, schemeDTO);
    }


}
