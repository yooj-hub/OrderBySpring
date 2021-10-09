package yooj.toyproject.orderbyspring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import yooj.toyproject.orderbyspring.domain.*;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.service.ItemService;
import yooj.toyproject.orderbyspring.service.MemberService;
import yooj.toyproject.orderbyspring.service.OrderItemService;
import yooj.toyproject.orderbyspring.service.OrderService;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderItemRepositoryImplTest {
    @Autowired
    ItemService itemService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;

    @Test
    void dtoTest() throws Exception {
        //given
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
        Order savedOrder = orderService.save(order);
        OrderItem orderItem = new OrderItem(savedOrder, savedItem.getPrice(), 10, savedItem);
        OrderItem savedOrderItem = orderItemService.save(orderItem);
        List<OrderItemDto> orderItemDtos = orderItemRepository.getOrderItemDto(savedOrder.getId());
        //when

        //then
    }

}
