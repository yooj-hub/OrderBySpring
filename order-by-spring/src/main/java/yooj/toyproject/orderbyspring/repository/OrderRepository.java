package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yooj.toyproject.orderbyspring.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
