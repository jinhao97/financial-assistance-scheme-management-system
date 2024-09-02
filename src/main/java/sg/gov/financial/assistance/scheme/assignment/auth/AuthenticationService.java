package sg.gov.financial.assistance.scheme.assignment.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.gov.financial.assistance.scheme.assignment.constant.RoleName;
import sg.gov.financial.assistance.scheme.assignment.dto.AuthenticationResponseDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.LoginDTO;
import sg.gov.financial.assistance.scheme.assignment.dto.RegisterRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.entity.AdministratorEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.RoleEntity;
import sg.gov.financial.assistance.scheme.assignment.repository.AdministratorRepository;

import java.util.ArrayList;
import java.util.List;

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

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        var role = new RoleEntity(RoleName.SUPERADMIN);
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(role);
        var newUser = new AdministratorEntity(requestDTO.getFirstname(), requestDTO.getLastname(), requestDTO.getEmail(),
                passwordEncoder.encode(requestDTO.getPassword()), roles);
        var savedUser = administratorRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        var refreshToken = jwtService.generateRefreshToken(newUser);
        return new AuthenticationResponseDTO(jwtToken, refreshToken);
    }

    public AuthenticationResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = administratorRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponseDTO(jwtToken, refreshToken);
    }
}
