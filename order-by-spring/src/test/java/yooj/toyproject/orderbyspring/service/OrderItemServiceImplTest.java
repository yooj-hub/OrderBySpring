package yooj.toyproject.orderbyspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.*;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class OrderItemServiceImplTest {

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

    @Test
    void 주문_아이템_생성_및_주문_총_가격_조회() throws Exception {
        //given
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);

        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member savedMember1 = memberService.save(member1);

        Order order = new Order(member1, OrderStatus.ACCEPTED);
        Order savedOrder = orderService.save(order);


        //when
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);

        //then
        assertThat(savedOrderItem.getOrder()).isEqualTo(savedOrder);
        assertThat(savedOrderItem.getTotalPrice()).isEqualTo(savedItem.getPrice() * 10);
        assertThat(savedItem.getStockQuantity()).isEqualTo(90);
        assertThat(savedOrderItem.getItem()).isEqualTo(savedItem);


    }

    @Test
    void 단일조회() throws Exception {
        //given

        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);

        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member savedMember1 = memberService.save(member1);

        Order order = new Order(savedMember1, OrderStatus.ACCEPTED);
        Order savedOrder = orderService.save(order);


        //when
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);

        //then
        OrderItem findOrderItem = orderItemService.findById(savedOrderItem.getId());
        assertThat(findOrderItem).isEqualTo(savedOrderItem);
    }

    @Test
    void 주문으로_조회() throws Exception {
        //given
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);

        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member member2 = new Member("j", "member2", "member2!", address);
        Member savedMember1 = memberService.save(member1);
        Member savedMember2 = memberService.save(member2);

        Order order1 = new Order(savedMember1, OrderStatus.ACCEPTED);
        Order order2 = new Order(savedMember2, OrderStatus.ACCEPTED);
        Order savedOrder1 = orderService.save(order1);
        Order savedOrder2 = orderService.save(order2);

        OrderItem orderItem1 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem2 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem3 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem4 = new OrderItem(savedOrder2, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem5 = new OrderItem(savedOrder2, savedItem.getPrice(), 10, savedItem);

        OrderItem savedOrderItem1 = orderItemService.save(orderItem1);
        OrderItem savedOrderItem2 = orderItemService.save(orderItem2);
        OrderItem savedOrderItem3 = orderItemService.save(orderItem3);
        OrderItem savedOrderItem4 = orderItemService.save(orderItem4);
        OrderItem savedOrderItem5 = orderItemService.save(orderItem5);

        //when
        List<OrderItem> findOrderItems1 = orderItemService.findByOrder(savedOrder1);
        List<OrderItem> findOrderItems2 = orderItemService.findByOrder(savedOrder2);

        //then
        assertThat(findOrderItems1.size()).isEqualTo(3);
        assertThat(findOrderItems1).contains(savedOrderItem1, savedOrderItem2, savedOrderItem3);
        assertThat(findOrderItems2.size()).isEqualTo(2);
        assertThat(findOrderItems2).contains(savedOrderItem4, savedOrderItem5);


    }

    @Test
    void 단일_삭제() throws Exception {
        //given
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);

        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        memberService.save(member1);

        Order order = new Order(member1, OrderStatus.ACCEPTED);
        Order savedOrder = orderService.save(order);

        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);

        //when
        orderItemService.cancelOne(savedOrderItem);

        //then
        assertThat(savedItem.getStockQuantity()).isEqualTo(100);
        assertThat(orderItemService.findById(savedOrderItem.getId())).isNull();

    }

    @Test
    void 전체_삭제() throws Exception {
        //given
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);

        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member savedMember1 = memberService.save(member1);

        Order order1 = new Order(savedMember1, OrderStatus.ACCEPTED);
        Order savedOrder1 = orderService.save(order1);

        OrderItem orderItem1 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem2 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);
        OrderItem orderItem3 = new OrderItem(savedOrder1, savedItem.getPrice(), 10, savedItem);

        orderItemService.save(orderItem1);
        orderItemService.save(orderItem2);
        orderItemService.save(orderItem3);

        //when
        Long cnt = orderItemService.cancelAll(savedOrder1);

        //then
        assertThat(cnt).isEqualTo(3);
        assertThat(savedItem.getStockQuantity()).isEqualTo(100);
        assertThat(savedOrder1.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }


}
