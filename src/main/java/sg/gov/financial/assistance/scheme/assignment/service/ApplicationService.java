package sg.gov.financial.assistance.scheme.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.repository.ApplicantRepository;

@Service
public class ApplicationService {

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicationService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }


}
