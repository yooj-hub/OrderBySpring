package yooj.toyproject.orderbyspring.repository;

import yooj.toyproject.orderbyspring.web.MemberResponseDto;

public interface MemberRepositoryCustom {
    MemberResponseDto login(String loginId, String password);
}
