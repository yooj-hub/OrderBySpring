package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.web.MemberResponseDto;

public interface LoginService {

    MemberResponseDto login(String loginId, String password);



}