package yooj.toyproject.orderbyspring.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.service.LoginService;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;
import yooj.toyproject.orderbyspring.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
        public String login(@RequestParam String loginId, @RequestParam String password, HttpServletRequest request){
        if(loginId==null || password==null){
            return "공백일 수 없습니다.";
        }
        MemberResponseDto loginMember = loginService.login(loginId, password);
        if(loginMember==null){
            return "아이디 또는 비밀번호가 맞지 않습니다.";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
        return "로그인 성공";

    }

    @PostMapping("/logout")
    public String logout(@RequestParam String loginId, @RequestParam String password, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session !=null){
            session.invalidate();
        }


        return "로그인 성공";

    }





}
