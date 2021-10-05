package yooj.toyproject.orderbyspring.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.repository.MemberRepository;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {
    private final MemberRepository memberRepository;
    private final JPAQueryFactory queryFactory;

    public LoginServiceImpl(MemberRepository memberRepository, EntityManager em) {
        this.memberRepository = memberRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberResponseDto login(String loginId, String password) {
        return null;

    }
}
