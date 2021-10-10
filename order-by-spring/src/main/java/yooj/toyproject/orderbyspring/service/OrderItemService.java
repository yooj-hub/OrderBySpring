package yooj.toyproject.orderbyspring.service;

import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem findById(Long orderItemId);

    List<OrderItem> findByOrder(Long orderId);

    Long cancelAll(Long orderId);

    void cancelOne(OrderItem orderItem);

    Integer getTotalOrderPrice(Long orderId);


    List<OrderItemDto> findOrderItemDto(Long orderId);

    List<OrderItem> findByOrderIdWithItem(Long orderId);

    void orderItemChange(Long orderId, List<OrderItemDto> orderItemDtoList);

    OrderItem makeOrderItem(Long orderId, int orderPrice, int quantity, Long itemId);

}
