package com.ispace.sensoraudit.configuration;

import com.ispace.sensoraudit.dto.UserResponse;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserResponse) {
                return Optional.of(((UserResponse) principal).getUserName());
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
