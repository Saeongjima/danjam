package site.danjam.mate.common.utils;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import site.danjam.mate.common.security.GlobalCustomUserDetails;

public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof GlobalCustomUserDetails userDetails) {
            return Optional.of(userDetails.getUsername());
        } else {
            return Optional.empty();
        }
    }
}