package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public List<ApplicantDTO> getAllHouseholdWithMembers() {
        var applicants = applicantRepository.findAllHouseholdsWithMembers();
        return applicants.stream()
                .map(this::convertToApplicantDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicantDTO createNewApplicant(ApplicantDTO applicantDTO) {
        // Create the head of household entity
        ApplicantEntity newApplicant = new ApplicantEntity(
                applicantDTO.getName(),
                applicantDTO.getRelationship(),
                applicantDTO.getSex(),
                applicantDTO.getDateOfBirth(),
                applicantDTO.getUin(),
                applicantDTO.getEmploymentStatus()
        );

        if (applicantDTO.getHouseholdMembers() != null) {
            List<ApplicantEntity> householdMembers = applicantDTO.getHouseholdMembers().stream()
                    .map(dto -> new ApplicantEntity(
                            dto.getName(),
                            dto.getRelationship(),
                            dto.getSex(),
                            dto.getDateOfBirth(),
                            dto.getUin(),
                            dto.getEmploymentStatus()
                    ))
                    .collect(Collectors.toList());

            householdMembers.forEach(member -> member.setHousehold(newApplicant));

            newApplicant.setHouseholdMembers(householdMembers);
        }

        // Save the head of household, which will also save household members due to CascadeType.ALL
        ApplicantEntity newApplicantEntity = applicantRepository.save(newApplicant);

        return convertToApplicantDTO(newApplicantEntity);
    }


    private ApplicantDTO convertToApplicantDTO(ApplicantEntity applicantEntity) {
        if (applicantEntity == null) return null;

        List<ApplicantDTO> householdMembers = applicantEntity.getHouseholdMembers() != null
                ? applicantEntity.getHouseholdMembers().stream().map(applicant -> new ApplicantDTO(
                applicant.getId(),
                applicant.getName(),
                applicant.getSex(),
                applicant.getDateOfBirth(),
                applicant.getUin(),
                applicant.getEmploymentStatus(),
                applicant.getRelationship()
        )).collect(Collectors.toList())
                : null;

        if (householdMembers != null) {
            return new ApplicantDTO(
                    applicantEntity.getId(),
                    applicantEntity.getName(),
                    applicantEntity.getSex(),
                    applicantEntity.getDateOfBirth(),
                    applicantEntity.getUin(),
                    applicantEntity.getEmploymentStatus(),
                    applicantEntity.getRelationship(),
                    householdMembers
            );
        }

        return new ApplicantDTO(
                applicantEntity.getId(),
                applicantEntity.getName(),
                applicantEntity.getSex(),
                applicantEntity.getDateOfBirth(),
                applicantEntity.getUin(),
                applicantEntity.getEmploymentStatus(),
                applicantEntity.getRelationship()
        );
    }
}
