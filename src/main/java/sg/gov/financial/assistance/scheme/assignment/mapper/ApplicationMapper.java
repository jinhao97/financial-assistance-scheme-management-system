package sg.gov.financial.assistance.scheme.assignment.mapper;

import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;

import java.util.Map;

public interface ApplicationMapper {

    ApplicationDTO toDTO(ApplicationEntity applicationEntity);

    ApplicationEntity toEntity(ApplicantEntity applicant, SchemeEntity scheme, Map<String, String> eligibleBenefits);
}
