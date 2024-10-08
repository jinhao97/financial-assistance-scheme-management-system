package sg.gov.financial.assistance.scheme.assignment.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.dto.AuthenticationResponseDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.LoginDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.RegisterRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.AdministratorEntity;
import sg.gov.financial.assistance.scheme.assignment.repository.AdministratorRepository;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AdministratorRepository administratorRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder, AdministratorRepository administratorRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.administratorRepository = administratorRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AdministratorEntity register(RegisterRequestDTO requestDTO) {
        var newUser = new AdministratorEntity(requestDTO.getUin(), passwordEncoder.encode(requestDTO.getPassword()));
        return administratorRepository.save(newUser);
    }

    public AuthenticationResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUin(),
                        request.getPassword()
                )
        );
        var user = administratorRepository.findByUin(request.getUin())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponseDTO(jwtToken, refreshToken);
    }
}
