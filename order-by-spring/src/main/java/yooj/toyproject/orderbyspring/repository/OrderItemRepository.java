package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yooj.toyproject.orderbyspring.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
