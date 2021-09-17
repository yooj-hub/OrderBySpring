package yooj.toyproject.orderbyspring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

    @Test
    void 아이템저장() throws Exception {
        //given
        Instrument ins1 = new Instrument("ins1", 1000, 1000, "a", LocalDateTime.now());
        Instrument ins2 = new Instrument("ins2", 2000, 2000, "a", LocalDateTime.now());
        //when
        Item savedIns1 = itemService.save(ins1);
        //then
        assertThat(itemService.findById(savedIns1.getId())).isEqualTo(savedIns1);
    }

    @Test
    void 단일조회() throws Exception {
        //given
        Instrument ins1 = new Instrument("ins1", 1000, 1000, "a", LocalDateTime.now());
        Instrument ins2 = new Instrument("ins2", 2000, 2000, "a", LocalDateTime.now());
        Item savedIns1 = itemService.save(ins1);
        Item savedIns2 = itemService.save(ins2);

        //when
        em.flush();
        em.clear();
        Item byId = itemService.findById(savedIns1.getId());
        List<Item> byName = itemService.findByName(savedIns2.getName());

        //then

        assertThat(byId.getId()).isEqualTo(savedIns1.getId());
        assertThat(byName.get(0).getId()).isEqualTo(savedIns2.getId());
    }

    @Test
    void 수량_변경() throws Exception {
        //given
        Instrument ins1 = new Instrument("ins1", 1000, 1000, "a", LocalDateTime.now());
        Item savedIns1 = itemService.save(ins1);
        ins1.changeStockQuantity(100);
        em.flush();
        em.clear();
        //when
        Item findItem = itemService.findById(savedIns1.getId());

        //then
        assertThat(findItem.getStockQuantity()).isEqualTo(900);
    }

    @Test
    void 아이템제거() throws Exception {
        //given
        Instrument ins1 = new Instrument("ins1", 1000, 1000, "a", LocalDateTime.now());
        Item savedIns1 = itemService.save(ins1);
        em.flush();
        em.clear();
        //when
        itemService.deleteItem(savedIns1);
        em.flush();
        em.clear();

        //then
        assertThat(itemService.findById(savedIns1.getId())).isNull();
    }

    @Test
    void 아이템이름포함조회() throws Exception {
        //given
        Instrument ins1 = new Instrument("aa1", 1000, 1000, "a", LocalDateTime.now());
        Instrument ins2 = new Instrument("aa2", 2000, 2000, "a", LocalDateTime.now());
        Instrument ins3 = new Instrument("bb1", 1000, 1000, "a", LocalDateTime.now());
        Instrument ins4 = new Instrument("bb2", 2000, 2000, "a", LocalDateTime.now());
        itemService.save(ins1);
        itemService.save(ins2);
        itemService.save(ins3);
        itemService.save(ins4);

        //when
        List<Item> searchByName1 = itemService.findAllByName(new ItemSearch("aa"));
        List<Item> searchByName2 = itemService.findAllByName(new ItemSearch("bb"));

        //then
        assertThat(searchByName1).contains(ins1,ins2);
        assertThat(searchByName1.size()).isEqualTo(2);
        assertThat(searchByName2).contains(ins3,ins4);
        assertThat(searchByName2.size()).isEqualTo(2);

    }


}
