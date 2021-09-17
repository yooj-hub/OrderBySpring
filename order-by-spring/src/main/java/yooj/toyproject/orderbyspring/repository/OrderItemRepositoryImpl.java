package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

import static yooj.toyproject.orderbyspring.domain.QOrderItem.orderItem;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<OrderItem> findByOrder(Order order) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory
                .selectFrom(orderItem)
                .where(orderItem.order.id.eq(order.getId()))
                .fetch();
    }

    @Override
    public Long cancelAll(Order order) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.delete(orderItem)
                .where(orderItem.order.id.eq(order.getId()))
                .execute();
    }
}
