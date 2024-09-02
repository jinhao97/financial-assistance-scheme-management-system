package sg.gov.financial.assistance.scheme.assignment.mapper;

import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;

public interface SchemeMapper {

    SchemeDTO toDTO(SchemeEntity schemeEntity, boolean showDetails);

    SchemeEntity toEntity(SchemeDTO schemeDTO);
}
