package edu.dongnao.rental.web.controller;

import edu.dongnao.rental.lang.ApiResponse;
import edu.dongnao.rental.lang.ServiceResult;
import edu.dongnao.rental.uc.api.ISmsService;
import edu.dongnao.rental.uc.api.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author International
 */
@Controller
public class UserController {

    @Reference
    private ISmsService smsService;
    @Reference
    private IUserService<?> userService;

    @GetMapping("/user/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping(value = "/sms/code")
    public @ResponseBody ApiResponse smsCode(@RequestParam("telephone") String telephone) {
        ServiceResult<String> result = smsService.sendSms(telephone);
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess("");
        } else {
            return ApiResponse.ofMessage(HttpStatus.SC_BAD_REQUEST, result.getMessage());
        }
    }
}
