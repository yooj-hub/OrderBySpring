package yooj.toyproject.orderbyspring.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yooj.toyproject.orderbyspring.service.LoginService;
import yooj.toyproject.orderbyspring.web.LoginMemberDto;
import yooj.toyproject.orderbyspring.web.SessionConst;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "form") LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        LoginMemberDto loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("{}",bindingResult);
        if(bindingResult.hasErrors()){
            return "/login/loginForm";
        }
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";

    }
    @GetMapping("/login")
//    @ResponseBody
    public String homeLogin(@Login LoginMemberDto loginMember, HttpServletRequest request, Model model) {
        if (loginMember == null) {
            model.addAttribute("form", new LoginForm());
            return "/login/loginForm";
        }
        model.addAttribute("member", loginMember);
        return "/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";

    }


}
