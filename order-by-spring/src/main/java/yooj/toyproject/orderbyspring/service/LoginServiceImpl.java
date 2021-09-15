package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {
    private final MemberRepository memberRepository;

    @Override
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);
    }
}
