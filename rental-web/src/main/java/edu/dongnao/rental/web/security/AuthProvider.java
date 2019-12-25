package edu.dongnao.rental.web.security;

import edu.dongnao.rental.uc.api.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Traveler
 * @date 2019/12/20 18:36
 */
public class AuthProvider implements AuthenticationProvider {

    @Reference
    private IUserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
