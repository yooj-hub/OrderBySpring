package yooj.toyproject.orderbyspring.repository;

import yooj.toyproject.orderbyspring.web.LoginMemberDto;

public interface MemberRepositoryCustom {
    LoginMemberDto login(String loginId, String password);
    String findByIdPassword(Long id);
}
