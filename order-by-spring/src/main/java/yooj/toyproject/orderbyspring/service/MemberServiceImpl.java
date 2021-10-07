package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public Member save(Member member) {
        Member saveMember = memberRepository.save(member);
        return member;

    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);

    }

    @Override
    public String findByIdPassword(Long id) {
        return memberRepository.findByIdPassword(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    @Override
    @Transactional
    public void changeMember(Long id, String username,  String password, String city, String street, String zipcode) {
        findById(id).changeMember(username,  password, city, street, zipcode);
    }


}
