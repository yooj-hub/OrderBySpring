package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem findById(Long orderItemId);

    List<OrderItem> findByOrder(Long orderId);

    Long cancelAll(Long orderId);

    void cancelOne(OrderItem orderItem);

    Integer getTotalOrderPrice(Long orderId);


}
