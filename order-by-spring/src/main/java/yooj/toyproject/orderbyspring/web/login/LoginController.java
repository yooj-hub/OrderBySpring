package yooj.toyproject.orderbyspring.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.service.LoginService;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;
import yooj.toyproject.orderbyspring.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "form") LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        MemberResponseDto loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(bindingResult.hasErrors()){
            return "/login/loginForm";
        }
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");

            return "/login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "로그인 성공";

    }
    @GetMapping("/login")
//    @ResponseBody
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberResponseDto loginMember, HttpServletRequest request, Model model) {
        if (loginMember == null) {
            model.addAttribute("form", new LoginForm());
            return "/login/loginForm";
        }
        model.addAttribute("member", loginMember);
        return "home";
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String loginId, @RequestParam String password, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "로그 아웃";

    }


}
