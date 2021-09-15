package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yooj.toyproject.orderbyspring.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
