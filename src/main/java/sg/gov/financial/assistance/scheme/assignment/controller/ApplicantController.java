package sg.gov.financial.assistance.scheme.assignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.service.ApplicantService;

import java.util.List;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public ResponseEntity<ApplicationResponse<List<ApplicantDTO>>> availableSchemes() {
        var result = applicantService.getAllHouseholdWithMembers();
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }

}
