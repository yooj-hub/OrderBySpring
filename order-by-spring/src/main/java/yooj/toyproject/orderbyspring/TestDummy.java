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
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("test")
public class TestDummy {

    private final MemberService memberService;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final EntityManager em;


    @PostConstruct
    @Transactional
    public void init() {
        Address address = new Address("seoul", "gwan", "12340");
        Member member = Member.builder()
                .username("testMember")
                .loginId("test")
                .password("test!")
                .address(address)
                .build();
        memberService.save(member);
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Instrument item2 = new Instrument("item2", 2000, 100, "dol", LocalDateTime.now());
        Item savedItem = itemService.save(item1);
        Item savedItem2 = itemService.save(item2);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order order2 = new Order(member, OrderStatus.ACCEPTED);
        Order order3 = new Order(member, OrderStatus.COMPLETED);
        Order order4 = new Order(member, OrderStatus.ONGOING);
        Order order5 = new Order(member, OrderStatus.WAITING);
        Order order6 = new Order(member, OrderStatus.WAITING);
        Order savedOrder = orderService.save(order);
        Order savedOrder2 = orderService.save(order2);
        Order savedOrder3 = orderService.save(order3);
        Order savedOrder4 = orderService.save(order4);
        Order savedOrder5 = orderService.save(order5);
        orderItemService.save(orderItemService.makeOrderItem(savedOrder.getId(), savedItem.getPrice(), 10, savedItem.getId()));
        orderItemService.save(orderItemService.makeOrderItem(savedOrder.getId(), savedItem2.getPrice(), 5, savedItem2.getId()));
        orderItemService.save(orderItemService.makeOrderItem(savedOrder3.getId(), savedItem.getPrice(), 10, savedItem.getId()));
        orderItemService.save(orderItemService.makeOrderItem(savedOrder4.getId(),savedItem2.getPrice(),10,savedItem2.getId()));
        orderItemService.save(orderItemService.makeOrderItem(savedOrder5.getId(), savedItem2.getPrice(), 10, savedItem2.getId()));
        orderItemService.save(orderItemService.makeOrderItem(savedOrder5.getId(), savedItem.getPrice(),5,savedItem.getId()));
        orderItemService.cancelAll(savedOrder2.getId());

    }

}
