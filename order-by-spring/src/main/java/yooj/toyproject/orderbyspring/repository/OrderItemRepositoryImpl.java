package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.domain.item.QItem;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static yooj.toyproject.orderbyspring.domain.QOrderItem.orderItem;
import static yooj.toyproject.orderbyspring.domain.item.QItem.item;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public OrderItemRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderItem> findByOrder(Long orderId) {
        return queryFactory
                .selectFrom(orderItem)
                .where(orderItem.order.id.eq(orderId))
                .fetch();
    }

    @Override
    public Long cancelAll(Order order) {
        return queryFactory.delete(orderItem)
                .where(orderItem.order.id.eq(order.getId()))
                .execute();
    }

    @Override
    public List<OrderItemDto> getOrderItemDto(Long orderId) {
        return queryFactory
                .select(
                        orderItem
                )
                .from(orderItem)
                .innerJoin(orderItem.item)
                .fetchJoin()
                .where(orderItem.order.id.eq(orderId))
                .fetch()
                .stream()
                .filter(oi -> oi.getQuantity() != 0)
                .map(oi -> {
                    String brand = "";
                    if (checkTypeInstrument(oi.getItem())) {
                        Instrument item = (Instrument) oi.getItem();
                        brand = item.getBrand();
                    }
                    return new OrderItemDto(oi.getId(),
                            oi.getTotalPrice(),
                            oi.getOrderPrice(),
                            oi.getQuantity(),
                            oi.getItem().
                                    getName(),
                            brand
                    );
                })
                .collect(toList());
    }

    private boolean checkTypeInstrument(Item item) {
        return item instanceof Instrument;
    }
    @Override
    public List<OrderItem> findByOrderIdWithItem(Long orderId){
        return queryFactory
                .select(orderItem)
                .from(orderItem)
                .join(orderItem.item, item)
                .fetchJoin()
                .where(orderItem.order.id.eq(orderId))
                .fetch();

    }
}
