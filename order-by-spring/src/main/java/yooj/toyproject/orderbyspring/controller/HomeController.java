package yooj.toyproject.orderbyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yooj.toyproject.orderbyspring.domain.RoleType;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;

@Controller
//@RestController
public class HomeController {
    @GetMapping("")
    public String home(@Login LoginMemberDto loginMember, Model model){
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("loginMember", loginMember);
        if(loginMember.getRoleType()== RoleType.ADMIN){
            return "adminHome";
        }
        return "loginHome";
    }
}
