package edu.dongnao.rental.web.controller;

import edu.dongnao.rental.uc.api.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Traveler
 * @since 2019/12/26 14:59
 */
@Controller
public class AdminController {
    @Reference
    private IUserService<?> userService;

    @GetMapping("/admin/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    @GetMapping("/admin/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }
}
