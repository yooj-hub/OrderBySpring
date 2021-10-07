package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);
    Member findById(Long id);
    String findByIdPassword(Long id);
    List<Member> findAll();
    void deleteMember(Member member);
    Optional<Member> findByLoginId(String loginId);
    void changeMember(Long id, String username, String password, String city, String street, String zipcode);

}
