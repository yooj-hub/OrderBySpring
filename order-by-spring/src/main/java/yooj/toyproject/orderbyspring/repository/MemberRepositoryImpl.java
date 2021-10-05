package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.QMember;
import yooj.toyproject.orderbyspring.web.MemberResponseDto;
import yooj.toyproject.orderbyspring.web.QMemberResponseDto;

import javax.persistence.EntityManager;

import java.util.Optional;

import static yooj.toyproject.orderbyspring.domain.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    public MemberResponseDto login(String loginId, String password) {
        return Optional.ofNullable(jpaQueryFactory
                .select(new QMemberResponseDto(
                        member.id,
                        member.username,
                        member.loginId,
                        member.address))
                .from(member)
                .where(member.loginId.eq(loginId).and(member.password.eq(password))
                ).fetchOne()).orElse(null);
//                .orElseThrow(()-> new IllegalArgumentException("아이디와 비밀번호를 확인해 주세요."));
    }

}
