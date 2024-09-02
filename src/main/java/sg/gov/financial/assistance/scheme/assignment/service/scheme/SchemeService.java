package sg.gov.financial.assistance.scheme.assignment.service.scheme;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.mapper.SchemeMapper;
import sg.gov.financial.assistance.scheme.assignment.repository.SchemeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;
    private final SchemeMapper schemeMapper;
    private final SchemeFactory schemeFactory;


    @Autowired
    public SchemeService(SchemeRepository schemeRepository, SchemeMapper schemeMapper, SchemeFactory schemeFactory) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
        this.schemeFactory = schemeFactory;
    }

    public List<SchemeDTO> getAllAvailableSchemes() {
        List<SchemeEntity> schemes = schemeRepository.findAllSchemesWithinCurrentDate();
        return schemes.stream()
                .map(schemeEntity -> schemeMapper.toDTO(schemeEntity, true))
                .collect(Collectors.toList());
    }

    public List<SchemeEntity> getSchemes() {
        return schemeRepository.findAllSchemesWithinCurrentDate();
    }

    public SchemeEntity getSchemeBySchemeName(String schemeName) {
        return Optional.ofNullable(schemeRepository.findBySchemeName(schemeName))
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Invalid scheme %s", schemeName)));
    }

    public SchemeDTO process(ApplicantEntity applicant, SchemeEntity scheme, List<HouseholdData> householdMembers) {
        var financialScheme = schemeFactory.getScheme(scheme.getSchemeName());
        return financialScheme.checkEligibility(applicant, scheme, householdMembers);
    }


}