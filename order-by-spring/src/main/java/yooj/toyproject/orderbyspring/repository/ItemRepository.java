package yooj.toyproject.orderbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yooj.toyproject.orderbyspring.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
