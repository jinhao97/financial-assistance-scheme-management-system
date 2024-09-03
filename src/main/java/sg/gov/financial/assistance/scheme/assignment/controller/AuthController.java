package sg.gov.financial.assistance.scheme.assignment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.financial.assistance.scheme.assignment.auth.AuthenticationService;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;
import sg.gov.financial.assistance.scheme.assignment.dto.ApplicationResponse;
import sg.gov.financial.assistance.scheme.assignment.dto.AuthenticationResponseDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.LoginDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.RegisterRequestDTO;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<String>> register(
            @RequestBody RegisterRequestDTO request
    ) {
        var result = authenticationService.register(request);
        var response = new ApplicationResponse<>(Status.SUCCESS, result != null ? "User has been successfully created" : "Error creating user");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<AuthenticationResponseDTO>> login(
            @RequestBody LoginDTO request
    ) {
        var result = authenticationService.login(request);
        var response = new ApplicationResponse<>(Status.SUCCESS, result);
        return ResponseEntity.ok(response);
    }
}
