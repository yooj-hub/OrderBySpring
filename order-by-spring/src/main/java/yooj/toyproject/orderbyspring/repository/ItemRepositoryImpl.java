package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import yooj.toyproject.orderbyspring.domain.OrderItem;
import yooj.toyproject.orderbyspring.domain.QOrderItem;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.domain.item.QItem;
import yooj.toyproject.orderbyspring.service.ItemSearch;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static yooj.toyproject.orderbyspring.domain.QOrderItem.orderItem;
import static yooj.toyproject.orderbyspring.domain.item.QItem.*;


public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findAllByName(ItemSearch search) {
        String str = search.getSearch();
        return queryFactory
                .selectFrom(item)
                .where(item.name.contains(str))
                .fetch();
    }

    @Override
    public List<Item> findByName(String name) {
        return queryFactory
                .selectFrom(item)
                .where(item.name.eq(name))
                .fetch();
    }

    @Override
    public List<Item> findByOrderId(Long orderId){
        return queryFactory.select(orderItem)
                .from(orderItem)
                .join(orderItem.item,item)
                .fetchJoin()
                .where(orderItem.order.id.eq(orderId))
                .fetch()
                .stream()
                .map(OrderItem::getItem).collect(toList());
    }

}
