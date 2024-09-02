package sg.gov.financial.assistance.scheme.assignment.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;
import sg.gov.financial.assistance.scheme.assignment.mapper.ApplicantMapper;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    public List<ApplicantDTO> getAllHouseholdWithMembers() {
        var applicants = applicantRepository.findAllHouseholdsWithMembers();
        return applicants.stream()
                .map(applicantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ApplicantEntity findApplicant(String uin) {
        return Optional.ofNullable(applicantRepository.findApplicantEntityByUin(uin))
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Applicant with UIN %s not found", uin)));
    }

    public List<HouseholdData> findHouseholdMembers(String uin) {
        return applicantRepository.findHouseholdWithMembersByUin(uin);
    }


    @Transactional
    public ApplicantDTO createNewApplicant(ApplicantDTO applicantDTO) {

        checkIfApplicantExists(applicantDTO.getUin());

        ApplicantEntity newApplicant = applicantMapper.toEntity(applicantDTO);

        if (applicantDTO.getHouseholdMembers() != null) {
            List<ApplicantEntity> householdMembers = applicantDTO.getHouseholdMembers().stream()
                    .map(applicantMapper::toEntity)
                    .collect(Collectors.toList());

            householdMembers.forEach(member -> member.setHousehold(newApplicant));
            newApplicant.setHouseholdMembers(householdMembers);
        }

        ApplicantEntity newApplicantEntity = applicantRepository.save(newApplicant);

        return applicantMapper.toDTO(newApplicantEntity);
    }

    private void checkIfApplicantExists(String uin) {
        var existingApplicant = Optional.ofNullable(applicantRepository.findApplicantEntityByUin(uin));
        if (existingApplicant.isPresent()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, String.format("Applicant %s already exists", uin));
        }
    }
}
