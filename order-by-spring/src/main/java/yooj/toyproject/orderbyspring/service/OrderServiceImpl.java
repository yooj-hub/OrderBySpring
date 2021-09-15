package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * can't find  -> return null
     */
    @Override
    public Order findById(Long orderId) {

        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> findAll() {

        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByMemberId(Long memberId) {

        return orderRepository.findByMemberId(memberId);
    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
