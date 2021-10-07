package yooj.toyproject.orderbyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;
import yooj.toyproject.orderbyspring.web.SessionConst;
import yooj.toyproject.orderbyspring.web.login.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@RestController
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }



}
