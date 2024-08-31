package sg.gov.financial.assistance.scheme.assignment.service;

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
