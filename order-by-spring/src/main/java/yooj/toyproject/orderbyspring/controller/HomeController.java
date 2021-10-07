package yooj.toyproject.orderbyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yooj.toyproject.orderbyspring.web.LoginMemberDto;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;

@Controller
//@RestController
public class HomeController {
    @GetMapping("/")
    public String home(@Login LoginMemberDto loginMember, Model model){
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("loginMember", loginMember);
        return "loginHome";
    }
}
