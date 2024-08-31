package sg.gov.financial.assistance.scheme.assignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ApplicationResponse<List<ApplicationDTO>>> getAllApplications() {
        var result = applicationService.fetchAllApplications();
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }

}
