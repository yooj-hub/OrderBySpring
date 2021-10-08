package yooj.toyproject.orderbyspring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.*;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.repository.ItemRepository;
import yooj.toyproject.orderbyspring.repository.MemberRepository;
import yooj.toyproject.orderbyspring.repository.OrderItemRepository;
import yooj.toyproject.orderbyspring.service.ItemService;
import yooj.toyproject.orderbyspring.service.MemberService;
import yooj.toyproject.orderbyspring.service.OrderItemService;
import yooj.toyproject.orderbyspring.service.OrderService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("test")
public class TestDummy {

    private final MemberService memberService;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final OrderService orderService;


    @PostConstruct
    @Transactional
    public void init() {
        Address address = new Address("seoul", "gwan", "12340");
        Member member = Member.builder()
                .username("esw")
                .loginId("test")
                .password("test!")
                .address(address)
                .build();
        memberService.save(member);
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order order2 = new Order(member, OrderStatus.ACCEPTED);
        Order order3 = new Order(member, OrderStatus.COMPLETED);
        Order savedOrder = orderService.save(order);
        Order savedOrder2 = orderService.save(order2);
        Order savedOrder3 = orderService.save(order3);
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);
        OrderItem orderItem2 = new OrderItem(savedOrder2, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem2 = orderItemService.save(orderItem2);
        OrderItem orderItem3 = new OrderItem(savedOrder3, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem3 = orderItemService.save(orderItem3);
        orderItemService.cancelAll(savedOrder2.getId());

    }

}
