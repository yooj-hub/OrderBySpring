package yooj.toyproject.orderbyspring.service;

import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.web.dto.InstrumentDto;

import java.time.LocalDate;
import java.util.List;

public interface ItemService {
    Item save(Item item);

    Item findById(Long itemId);

    List<Item> findAll();

    List<Item> findByName(String name);

    void deleteItem(Item item);

    List<Item> findAllByName(ItemSearch search);

    List<Item> findByOrderId(Long orderId);

    void updateStockQuantity(Long itemId, int quantity);

    void updateStockQuantity(Item item, int quantity);

    List<InstrumentDto> findAllInstrumentDto();

    void instrumentChange(Long itemId, String name, int price, int stockQuantity, String brand, LocalDate manufacturingDate);
}
