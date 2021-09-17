package yooj.toyproject.orderbyspring.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.domain.item.QItem;
import yooj.toyproject.orderbyspring.service.ItemSearch;

import javax.persistence.EntityManager;
import java.util.List;

import static yooj.toyproject.orderbyspring.domain.item.QItem.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Item> findAllByName(ItemSearch search) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        String str = search.getSearch();
        return queryFactory
                .selectFrom(item)
                .where(item.name.contains(str))
                .fetch();
    }

    @Override
    public List<Item> findByName(String name) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory
                .selectFrom(item)
                .where(item.name.eq(name))
                .fetch();
    }
}
