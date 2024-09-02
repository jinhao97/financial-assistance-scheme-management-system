package sg.gov.financial.assistance.scheme.assignment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicantDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.service.ApplicationService;
import sg.gov.financial.assistance.scheme.assignment.service.FinancialSchemeFacade;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final FinancialSchemeFacade financialSchemeFacade;

    @Autowired
    public ApplicationController(ApplicationService applicationService, FinancialSchemeFacade financialSchemeFacade) {
        this.applicationService = applicationService;
        this.financialSchemeFacade = financialSchemeFacade;
    }

    @Operation(summary = "Get all applications",
            description = "Retrieves a list of all applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of applications",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<ApplicationDTO>>> getAllApplications() {
        var result = applicationService.fetchAllApplications();
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Create a new application",
            description = "Creates a new application with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<ApplicationResponse<ApplicationDTO>> createApplication(@RequestBody ApplicationRequestDTO request) {
        var result = financialSchemeFacade.createApplication(request);
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }

}
