package io.cisa.taxiiserver.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import io.cisa.taxiiserver.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return userName != null ? userName : Constants.SYSTEM_ACCOUNT;
    }
}