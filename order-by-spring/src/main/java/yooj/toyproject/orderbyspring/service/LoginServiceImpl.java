package yooj.toyproject.orderbyspring.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;

import javax.persistence.EntityManager;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto login(String loginId, String password) {
        Member loginMember = memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);
        log.info("{}",loginMember);
        if (loginMember == null) {
            return null;
        }
        return new MemberResponseDto(loginMember);

    }
}
