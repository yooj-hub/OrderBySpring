package yooj.toyproject.orderbyspring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.*;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.service.ItemService;
import yooj.toyproject.orderbyspring.service.MemberService;
import yooj.toyproject.orderbyspring.service.OrderItemService;
import yooj.toyproject.orderbyspring.service.OrderService;
import yooj.toyproject.orderbyspring.web.dto.OrderListDto;
import yooj.toyproject.orderbyspring.web.dto.OrderListQueryDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryImplTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;


    @BeforeEach
    void before() {
        orderRepository.deleteAll();

    }

    @AfterEach
    void after() {
        orderRepository.deleteAll();
    }

    @Test
    void find_queryList() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member = Member.builder()
                .username("esw")
                .loginId("test")
                .password("test!")
                .address(address)
                .build();
        Member savedMember = memberService.save(member);
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order savedOrder = orderService.save(order);
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);
        em.flush();
        em.clear();
        List<OrderListQueryDto> orderListQueryDtoByMemberId = orderRepository.findOrderListQueryDtoByMemberId(savedMember.getId());
        OrderListQueryDto orderListQueryDto = orderListQueryDtoByMemberId.get(0);
        //when

        //then
        assertThat(savedOrder.getId()).isEqualTo(orderListQueryDto.getOrderId());
        assertThat(savedOrder.getStatus()).isEqualTo(orderListQueryDto.getStatus());
        assertThat(savedMember.getUsername()).isEqualTo(orderListQueryDto.getUsername());
        assertThat(address.getCity()).isEqualTo(orderListQueryDto.getAddress().getCity());

    }

    @Test
    void find_orderList() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member = Member.builder()
                .username("esw")
                .loginId("test")
                .password("test!")
                .address(address)
                .build();
        Member savedMember = memberService.save(member);
        Instrument item1 = new Instrument("item1", 1000, 100, "sang", LocalDateTime.now());
        Item savedItem = itemService.save(item1);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order savedOrder = orderService.save(order);
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);
        em.flush();
        em.clear();
        //when
        List<OrderListDto> orderListDtos = orderService.findOrderListDto(savedMember.getId());
        OrderListDto orderListDto = orderListDtos.get(0);
        //then
        assertThat(orderListDtos.size()).isEqualTo(1);
        assertThat(orderListDto.getOrderId()).isEqualTo(savedOrder.getId());
        assertThat(orderListDto.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(orderListDto.getTotalPrice()).isEqualTo(orderService.findOrderPrice(savedOrder.getId()));
        assertThat(orderListDto.getStatus()).isEqualTo(savedOrder.getStatus());


    }


}
