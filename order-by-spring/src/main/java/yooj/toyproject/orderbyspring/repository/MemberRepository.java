package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yooj.toyproject.orderbyspring.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.loginId = :loginId")
    Optional<Member> findByLoginId(String loginId);
}
