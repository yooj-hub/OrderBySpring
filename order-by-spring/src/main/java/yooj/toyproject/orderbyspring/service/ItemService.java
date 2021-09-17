package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.item.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);
    Item findById(Long itemId);
    List<Item> findAll();
    List<Item> findByName(String name);
    void deleteItem(Item item);
    List<Item> findAllByName(ItemSearch search);

}
