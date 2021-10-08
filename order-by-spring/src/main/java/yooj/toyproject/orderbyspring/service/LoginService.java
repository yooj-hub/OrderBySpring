package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

public interface LoginService {

    LoginMemberDto login(String loginId, String password);



}
