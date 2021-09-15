package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.Member;

import java.util.List;

public interface MemberService {
    Member save(Member member);
    Member findById(Long id);
    List<Member> findAll();
    void deleteMember(Member member);

}
