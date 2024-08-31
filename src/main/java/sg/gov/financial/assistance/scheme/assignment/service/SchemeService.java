package sg.gov.financial.assistance.scheme.assignment.service;


import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.CriteriaAttributesValuesEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.repository.SchemeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;

    public SchemeService(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    public List<SchemeDTO> getAllAvailableSchemes() {
        List<SchemeEntity> schemes = schemeRepository.findAllSchemesWithinCurrentDate();
        return schemes.stream()
                .map(this::convertToSchemeDTO)
                .collect(Collectors.toList());
    }

    private SchemeDTO convertToSchemeDTO(SchemeEntity scheme) {

        Map<String, String> criteriaMap = new HashMap<>();

        if (scheme.getCriteriaAttributesValues() != null) {
            criteriaMap = scheme.getCriteriaAttributesValues().stream()
                    .collect(Collectors.toMap(
                            value -> value.getCriteriaAttributesEntity().getCriteriaName(), // or any unique identifier
                            CriteriaAttributesValuesEntity::getCriteriaValue
                    ));
        }
        return new SchemeDTO(
                scheme.getId(), scheme.getSchemeName(),
                scheme.getDescription(), scheme.getDisplayName(),
                scheme.getStartDate(), scheme.getEndDate(),
                criteriaMap);
    }
}