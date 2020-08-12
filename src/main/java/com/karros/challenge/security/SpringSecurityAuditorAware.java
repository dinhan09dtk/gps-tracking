package com.karros.challenge.security;

import com.karros.challenge.config.Constants;

import java.util.Optional;

import com.karros.challenge.domain.User;
import com.karros.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {
        String login = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT);
        return userRepository.findOneByLogin(login);
    }
}
