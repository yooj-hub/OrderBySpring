package yooj.toyproject.orderbyspring.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.BaseEntity;
import yooj.toyproject.orderbyspring.domain.item.Item;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(Order order, int orderPrice, int quantity, Item item) {
        this.totalPrice = orderPrice* quantity;
        this.order = order;
        this.quantity = quantity;
        this.item = item;
        this.orderPrice = orderPrice;
        item.swapQuantity(item.getStockQuantity()-quantity);
    }

    public void changeOrderQuantity(int quantity) {
        this.quantity=quantity;
    }
//    public void changeQuantity(int quantity){
//        item.changeStockQuantity(quantity-this.quantity);
//        this.quantity=quantity;
//        this.totalPrice=quantity*this.orderPrice;
//    }
}
