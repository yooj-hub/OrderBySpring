package yooj.toyproject.orderbyspring.repository;

import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.service.ItemSearch;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findAllByName(ItemSearch search);
    List<Item> findByName(String name);
}
