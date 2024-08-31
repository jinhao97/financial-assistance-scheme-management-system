package sg.gov.financial.assistance.scheme.assignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.dto.SchemeDTO;
import sg.gov.financial.assistance.scheme.assignment.service.SchemeService;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    @Autowired
    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping
    public ResponseEntity<ApplicationResponse<List<SchemeDTO>>> availableSchemes() {
        var result = schemeService.getAllAvailableSchemes();
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }


}
