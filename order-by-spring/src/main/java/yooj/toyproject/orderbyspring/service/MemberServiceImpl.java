package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;

import java.util.List;

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
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

}
