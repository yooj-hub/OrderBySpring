package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yooj.toyproject.orderbyspring.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    @Query("select m from Member m where m.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);
}
