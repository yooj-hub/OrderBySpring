package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);

    Order findById(Long orderId);

    List<Order> findAll();

    List<Order> findByMemberId(Long memberId);

    void deleteOrder(Order order);


}
