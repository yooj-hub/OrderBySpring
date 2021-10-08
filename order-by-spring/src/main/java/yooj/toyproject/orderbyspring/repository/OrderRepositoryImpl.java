package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.QMember;
import yooj.toyproject.orderbyspring.domain.QOrder;
import yooj.toyproject.orderbyspring.domain.QOrderItem;
import yooj.toyproject.orderbyspring.web.dto.OrderListQueryDto;
import yooj.toyproject.orderbyspring.web.dto.QOrderListQueryDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static yooj.toyproject.orderbyspring.domain.QMember.member;
import static yooj.toyproject.orderbyspring.domain.QOrder.order;
import static yooj.toyproject.orderbyspring.domain.QOrderItem.orderItem;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderListQueryDto> findOrderListQueryDtoByMemberId(Long memberId) {
        return queryFactory.
                selectFrom(order)
                .innerJoin(order.member, member)
                .fetchJoin()
                .where(order.member.id.eq(memberId))
                .fetch()
                .stream()
                .map(o-> new OrderListQueryDto(o.getId(),o.getMember().getUsername(),o.getStatus(),o.getAddress(),o.getCreatedDate()))
                .collect(toList());

//        queryFactory.select(
//                new QOrderListQueryDto(
//                        order.id,
//                        member.username,
//                        order.status,
//                        order.address
//                )
//        )
//                .from(order)
//                .innerJoin(order.member,member)
////                .fetchJoin()
//                .where(order.member.id.eq(memberId))
//                .fetch();

    }

    @Override
    public Integer findOrderPrice(Long orderId) {
        return queryFactory
                .select(orderItem.totalPrice.sum())
                .from(orderItem)
                .where(orderItem.order.id.eq(orderId))
                .fetchOne();
    }
}
