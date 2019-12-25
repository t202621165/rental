package edu.dongnao.rental.web.security;

import edu.dongnao.rental.uc.api.IUserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Traveler
 * @date 2019/12/20 15:31
 */
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    IUserService userService;
}
