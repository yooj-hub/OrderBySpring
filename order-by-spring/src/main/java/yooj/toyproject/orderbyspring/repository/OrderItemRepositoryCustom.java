package yooj.toyproject.orderbyspring.repository;

import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findByOrder(Long orderId);
    Long cancelAll(Order order);

    List<OrderItemDto> getOrderItemDto(Long orderId);

    List<OrderItem> findByOrderIdWithItem(Long orderId);
}
