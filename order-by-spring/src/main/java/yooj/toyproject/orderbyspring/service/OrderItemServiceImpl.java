package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.domain.OrderStatus;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.repository.ItemRepository;
import yooj.toyproject.orderbyspring.repository.OrderItemRepository;
import yooj.toyproject.orderbyspring.repository.OrderRepository;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

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
        findByOrderIdWithItem(orderId)
                .forEach(oi -> oi.getItem().swapQuantity(oi.getItem().getStockQuantity() + oi.getQuantity()));
        return orderItemRepository.cancelAll(order);
    }

    @Override
    @Transactional
    public void cancelOne(OrderItem orderItem) {
        orderItem.getItem().swapQuantity(orderItem.getItem().getStockQuantity() + orderItem.getQuantity());
        orderItemRepository.delete(orderItem);
    }

    @Override
    public Integer getTotalOrderPrice(Long orderId) {
        return findByOrder(orderId).stream().map(OrderItem::getTotalPrice).reduce(Integer::sum).orElse(0);
    }

    @Override
    public List<OrderItemDto> findOrderItemDto(Long orderId) {
        return orderItemRepository.getOrderItemDto(orderId);
    }

    @Override
    public List<OrderItem> findByOrderIdWithItem(Long orderId) {
        return orderItemRepository.findByOrderIdWithItem(orderId);
    }

    @Override
    @Transactional
    public void orderItemChange(Long orderId, List<OrderItemDto> orderItemDtoList) {
        List<OrderItem> orderItem = orderItemRepository.findByOrderIdWithItem(orderId);
        int size = orderItem.size();
        for (int i = 0; i < size; i++) {
            if (orderItem.get(i).getQuantity() == orderItemDtoList.get(i).getQuantity()) {
                continue;
            }
            if (orderItemDtoList.get(i).getQuantity() == 0) {
                cancelOne(findById(orderItemDtoList.get(i).getId()));
                continue;
            }
            Item item = orderItem.get(i).getItem();
            item.swapQuantity(item.getStockQuantity() + orderItem.get(i).getQuantity() - orderItemDtoList.get(i).getQuantity());
            ;
            orderItem.get(i).changeOrderQuantity(orderItemDtoList.get(i).getQuantity());

        }

    }

    @Override
    @Transactional
    public OrderItem makeOrderItem(Long orderId, int orderPrice, int quantity, Long itemId) {
        return new OrderItem(orderRepository.findById(orderId).orElse(null), orderPrice, quantity, itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("item is null")));
    }

}
