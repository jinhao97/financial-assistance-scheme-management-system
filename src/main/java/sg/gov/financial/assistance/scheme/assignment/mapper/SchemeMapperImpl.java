package sg.gov.financial.assistance.scheme.assignment.mapper;

import org.springframework.stereotype.Component;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.BenefitAttributesValuesEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.CriteriaAttributesValuesEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SchemeMapperImpl implements SchemeMapper {

    @Override
    public SchemeDTO toDTO(SchemeEntity schemeEntity, boolean showDetails) {
        Map<String, String> criteriaMap = extractCriteriaMap(schemeEntity);
        Map<String, String> benefitMap = extractBenefitMap(schemeEntity);

        return new SchemeDTO(
                schemeEntity.getId(), schemeEntity.getSchemeName(),
                schemeEntity.getDescription(), schemeEntity.getDisplayName(),
                schemeEntity.getStartDate(), schemeEntity.getEndDate(),
                showDetails ? criteriaMap : null,
                showDetails ? benefitMap : null);
    }

    @Override
    public SchemeEntity toEntity(SchemeDTO schemeDTO) {
        return null;
    }

    private Map<String, String> extractCriteriaMap(SchemeEntity schemeEntity) {
        if (schemeEntity.getCriteriaAttributesValues() == null) {
            return Collections.emptyMap();
        }

        return schemeEntity.getCriteriaAttributesValues().stream()
                .collect(Collectors.toMap(
                        value -> value.getCriteriaAttributesEntity().getCriteriaName(),
                        CriteriaAttributesValuesEntity::getCriteriaValue
                ));
    }

    private Map<String, String> extractBenefitMap(SchemeEntity schemeEntity) {
        if (schemeEntity.getBenefitAttributesValues() == null) {
            return Collections.emptyMap();
        }

        return schemeEntity.getBenefitAttributesValues().stream()
                .collect(Collectors.toMap(
                        value -> value.getBenefitAttributesEntity().getBenefitName(),
                        BenefitAttributesValuesEntity::getBenefitValue
                ));
    }
}
