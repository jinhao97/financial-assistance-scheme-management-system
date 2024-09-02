package sg.gov.financial.assistance.scheme.assignment.mapper;

import org.springframework.stereotype.Component;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ApplicantMapperImpl implements ApplicantMapper {

    @Override
    public ApplicantDTO toDTO(ApplicantEntity entity) {
        if (entity == null) return null;

        List<ApplicantDTO> householdMembers = convertHouseholdMembersToDTO(entity);

        return createApplicantDTO(entity, householdMembers);
    }

    @Override
    public ApplicantEntity toEntity(ApplicantDTO dto) {
        return new ApplicantEntity(
                dto.getName(),
                dto.getRelationship(),
                dto.getSex(),
                dto.getDateOfBirth(),
                dto.getUin(),
                dto.getEmploymentStatus(),
                dto.getMaritialStatus()
        );
    }

    private List<ApplicantDTO> convertHouseholdMembersToDTO(ApplicantEntity applicantEntity) {
        if (applicantEntity.getHouseholdMembers() == null) {
            return Collections.emptyList();
        }

        return applicantEntity.getHouseholdMembers().stream()
                .map(member -> new ApplicantDTO(
                        member.getId(),
                        member.getName(),
                        member.getSex(),
                        member.getDateOfBirth(),
                        member.getUin(),
                        member.getEmploymentStatus(),
                        member.getMaritialStatus(),
                        member.getRelationship()
                ))
                .collect(Collectors.toList());
    }

    private ApplicantDTO createApplicantDTO(ApplicantEntity applicantEntity, List<ApplicantDTO> householdMembers) {
        return new ApplicantDTO(
                applicantEntity.getId(),
                applicantEntity.getName(),
                applicantEntity.getSex(),
                applicantEntity.getDateOfBirth(),
                applicantEntity.getUin(),
                applicantEntity.getEmploymentStatus(),
                applicantEntity.getRelationship(),
                applicantEntity.getMaritialStatus(),
                householdMembers
        );
    }

}
