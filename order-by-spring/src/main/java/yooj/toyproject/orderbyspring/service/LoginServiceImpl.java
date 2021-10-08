package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final MemberRepository memberRepository;

    @Override
    public LoginMemberDto login(String loginId, String password) {
        Member loginMember = memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);
        log.info("{}",loginMember);
        if (loginMember == null) {
            return null;
        }
        return new LoginMemberDto(loginMember);

    }
}
