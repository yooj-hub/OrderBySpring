package yooj.toyproject.orderbyspring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderStatus;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    void 주문생성() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member member2 = new Member("j", "member2", "member2!", address);
        memberService.save(member1);
        memberService.save(member2);
        Order order = new Order(member1, OrderStatus.ACCEPTED);

        //when
        Order saveOrder = orderService.save(order);

        //then
        assertThat(saveOrder).isEqualTo(order);
    }

    @Test
    void 주문단일조회() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        memberService.save(member1);
        Order order = new Order(member1, OrderStatus.ACCEPTED);

        //when
        Order saveOrder = orderService.save(order);
        Order findOrder = orderService.findById(saveOrder.getId());

        //then
        assertThat(saveOrder).isEqualTo(findOrder);
        assertThat(findOrder.getMember()).isEqualTo(member1);
    }

    @Test
    void 주문전체조회() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member member2 = new Member("j", "member2", "member2!", address);
        memberService.save(member1);
        Member saveMember2 = memberService.save(member2);
        Order order1 = new Order(member1, OrderStatus.ACCEPTED);
        Order order2 = new Order(member2, OrderStatus.ACCEPTED);
        Order order3 = new Order(member2, OrderStatus.ACCEPTED);

        //when
        Order saveOrder1 = orderService.save(order1);
        Order saveOrder2 = orderService.save(order2);
        Order saveOrder3 = orderService.save(order3);
        List<Order> orders = orderService.findAll();
        List<Order> member2Order = orderService.findByMemberId(saveMember2.getId());

        //then
        assertThat(orders).contains(saveOrder1, saveOrder2, saveOrder3);
        assertThat(member2Order.size()).isEqualTo(2);

    }

    @Test
    void 주문상태변경() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member = new Member("y", "member1", "member1!", address);
        memberService.save(member);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order saveOrder = orderService.save(order);
        //when
        saveOrder.changeOrderStatus(OrderStatus.CANCEL);
        em.flush();
        em.clear();


        //then
        assertThat(orderService.findById(saveOrder.getId()).getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    void 주문_삭제() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member = new Member("y", "member1", "member1!", address);
        memberService.save(member);
        Order order = new Order(member, OrderStatus.ACCEPTED);
        Order saveOrder = orderService.save(order);

        //when
        em.flush();
        em.clear();
        orderService.deleteOrder(saveOrder);
        em.flush();
        em.clear();

        //then
        assertThat(orderService.findById(saveOrder.getId())).isNull();
    }


}
