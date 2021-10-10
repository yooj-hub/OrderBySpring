package yooj.toyproject.orderbyspring.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import yooj.toyproject.orderbyspring.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;

    private int price;
    private int stockQuantity;

    public void initItem(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public void swapQuantity(int stockQuantity){
        this.stockQuantity=stockQuantity;
    };
}
