package sg.gov.financial.assistance.scheme.assignment.mapper;

import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;

public interface ApplicantMapper {

    ApplicantDTO toDTO(ApplicantEntity entity);

    ApplicantEntity toEntity(ApplicantDTO dto);
}
