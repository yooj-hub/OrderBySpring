package yooj.toyproject.orderbyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RestController
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false)Member loginMember, HttpServletRequest request, Model model){
        if(loginMember==null){
            return "login fail";
        }

        model.addAttribute("member",loginMember);
        return "login Ok";

    }

}
