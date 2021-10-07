package yooj.toyproject.orderbyspring.web.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import yooj.toyproject.orderbyspring.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestUri = request.getRequestURI();
        HttpSession session = request.getSession();


        if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            response.sendRedirect("/login?redirectURL");
            //로그인 창으로 보내기
            return false;
        }

        return true;


    }
}
