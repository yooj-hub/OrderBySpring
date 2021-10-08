package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.domain.OrderStatus;
import yooj.toyproject.orderbyspring.repository.OrderItemRepository;
import yooj.toyproject.orderbyspring.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem findById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElse(null);
    }

    @Override
    public List<OrderItem> findByOrder(Long orderId) {
        return orderItemRepository.findByOrder(orderId);
    }

    @Override
    @Transactional
    public Long cancelAll(Long orderId) {
        Order order = orderRepository.getById(orderId);
        order.changeOrderStatus(OrderStatus.CANCEL);
        findByOrder(orderId)
                .forEach(o -> o.getItem().changeStockQuantity(-1 * o.getQuantity()));
        return orderItemRepository.cancelAll(order);
    }

    @Override
    @Transactional
    public void cancelOne(OrderItem orderItem) {
        orderItem.getItem().changeStockQuantity(orderItem.getQuantity() * -1);
        orderItemRepository.delete(orderItem);
    }

    @Override
    public Integer getTotalOrderPrice(Long orderId) {
        return findByOrder(orderId).stream().map(OrderItem::getTotalPrice).reduce(Integer::sum).orElse(0);
    }
}
