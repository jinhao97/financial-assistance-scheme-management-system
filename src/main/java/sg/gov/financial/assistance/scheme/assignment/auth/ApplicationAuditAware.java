package sg.gov.financial.assistance.scheme.assignment.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sg.gov.financial.assistance.scheme.assignment.entity.AdministratorEntity;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        AdministratorEntity userPrincipal = (AdministratorEntity) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }
}