package yooj.toyproject.orderbyspring.repository;

import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findByOrder(Order order);
    Long cancelAll(Order order);

}