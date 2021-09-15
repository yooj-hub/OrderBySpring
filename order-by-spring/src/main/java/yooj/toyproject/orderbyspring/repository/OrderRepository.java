package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yooj.toyproject.orderbyspring.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.member.id= :memberId")
    List<Order> findByMemberId(@Param("memberId") Long memberId);
}
