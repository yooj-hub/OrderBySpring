package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem findById(Long orderItemId);

    List<OrderItem> findByOrder(Order order);

    Long cancelAll(Order order);

    void cancelOne(OrderItem orderItem);

    Integer getTotalOrderPrice(Order order);


}
