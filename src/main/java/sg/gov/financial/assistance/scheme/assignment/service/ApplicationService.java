package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.mapper.ApplicationMapper;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicationRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    @Transactional
    public List<ApplicationDTO> fetchAllApplications() {
        try {
            List<ApplicationEntity> applications = applicationRepository.findAll();
            return applications.stream()
                    .map(applicationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ApplicationEntity findExistingApplication(Long schemeId, Long applicantId) {
        return applicationRepository.findApplicationEntitiesBySchemeEntityIdAndApplicantEntityId(schemeId, applicantId);
    }

    @Transactional
    public ApplicationDTO saveApplication(ApplicantEntity applicant, SchemeEntity scheme, Map<String, String> eligibleBenefits) {

        var applicationEntity = applicationMapper.toEntity(applicant, scheme, eligibleBenefits);

        var newApplication = applicationRepository.save(applicationEntity);

        return applicationMapper.toDTO(newApplication);
    }


}
