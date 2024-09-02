package sg.gov.financial.assistance.scheme.assignment.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sg.gov.financial.assistance.scheme.assignment.constant.ApplicationStatus;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.BenefitDetailsDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.BenefitsDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    private final ObjectMapper objectMapper;
    private final ApplicantMapper applicantMapper;
    private final SchemeMapper schemeMapper;

    public ApplicationMapperImpl(ObjectMapper objectMapper, ApplicantMapper applicantMapper, SchemeMapper schemeMapper) {
        this.objectMapper = objectMapper;
        this.applicantMapper = applicantMapper;
        this.schemeMapper = schemeMapper;
    }

    @Override
    public ApplicationDTO toDTO(ApplicationEntity applicationEntity) {
        try {
            var applicationDTO = applicantMapper.toDTO(applicationEntity.getApplicantEntity());
            var schemeDTO = schemeMapper.toDTO(applicationEntity.getSchemeEntity(), false);
            var benefits = objectMapper.readValue(applicationEntity.getBenefitDetails(), BenefitsDTO.class);
            return new ApplicationDTO(applicationEntity.getId(), applicationDTO, schemeDTO, benefits);
        } catch (Exception ex) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public ApplicationEntity toEntity(ApplicantEntity applicant, SchemeEntity scheme, Map<String, String> eligibleBenefits) {

        var benefitsDTO = new BenefitsDTO(convertToBenefitDetailsDTO(eligibleBenefits));

        try {
            return new ApplicationEntity(applicant, scheme, objectMapper.writeValueAsString(benefitsDTO), LocalDateTime.now(), ApplicationStatus.PENDING);
        } catch (Exception ex) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Error serializing benefits data.");
        }
    }

    private List<BenefitDetailsDTO> convertToBenefitDetailsDTO(Map<String, String> eligibleBenefitsMap) {
        return eligibleBenefitsMap.entrySet().stream()
                .map(entry -> new BenefitDetailsDTO(entry.getKey(), parseAmount(entry.getValue())))
                .collect(Collectors.toList());
    }

    private BigDecimal parseAmount(String amountStr) {
        try {
            return new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
