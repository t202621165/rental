package edu.dongnao.rental.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Traveler
 * @date 2019/12/20 15:50
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @SuppressWarnings("unchecked")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String uri = request.getRequestURI().replace(request.getContextPath(), "");

        if ("/admin/login".equals(uri)) {
            List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) authentication.getAuthorities();
            grantedAuthorityList.forEach((g) -> {
                if ("ADMIN".equals(g.getAuthority())) {
                    try {
                        response.sendRedirect("/admin/center");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
