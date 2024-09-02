package sg.gov.financial.assistance.scheme.assignment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.dto.EligibleSchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.service.FinancialSchemeFacade;
import sg.gov.financial.assistance.scheme.assignment.service.scheme.SchemeService;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    private final SchemeService schemeService;
    private final FinancialSchemeFacade financialSchemeFacade;

    @Autowired
    public SchemeController(SchemeService schemeService, FinancialSchemeFacade financialSchemeFacade) {
        this.schemeService = schemeService;
        this.financialSchemeFacade = financialSchemeFacade;
    }

    @Operation(summary = "Get all available financial assistance schemes",
            description = "Retrieves a list of all available financial assistance schemes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of schemes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<SchemeDTO>>> availableSchemes() {
        var result = schemeService.getAllAvailableSchemes();
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get eligible schemes for an applicant",
            description = "Returns a list of eligible schemes for a given applicant based on their UIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved eligible schemes",
                    content = @Content(schema = @Schema(implementation = ApplicationResponse.class))),
    })
    @GetMapping("/eligible")
    public ResponseEntity<ApplicationResponse<EligibleSchemeDTO>> getEligibleSchemes(@RequestParam("applicant") String uin) {
        var result = financialSchemeFacade.showApplicantEligibleSchemes(uin);
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }
}
