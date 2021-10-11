package yooj.toyproject.orderbyspring.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.repository.ItemRepository;
import yooj.toyproject.orderbyspring.web.dto.InstrumentDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public List<Item> findAllByName(ItemSearch search) {
        return itemRepository.findAllByName(search);
    }

    @Override
    public List<Item> findByOrderId(Long orderId) {
        return itemRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public void updateStockQuantity(Long itemId, int quantity) {
        findById(itemId).swapQuantity(quantity);
    }

    @Override
    @Transactional
    public void updateStockQuantity(Item item, int quantity) {
        findById(item.getId()).swapQuantity(quantity);
    }

    @Override
    public List<InstrumentDto> findAllInstrumentDto() {
        return itemRepository.findAllInstrumentDto();
    }

    @Override
    @Transactional
    public void instrumentChange(Long itemId, String name, int price, int stockQuantity, String brand, LocalDate manufacturingDate) {
        Instrument instrument = (Instrument) itemRepository.findById(itemId).orElse(null);
        if(instrument==null){
            return;
        }
        instrument.updateInstrument(name,price,stockQuantity,brand,manufacturingDate);
    }


}
