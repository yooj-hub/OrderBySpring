package yooj.toyproject.orderbyspring.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yooj.toyproject.orderbyspring.domain.item.Item;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

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

    public OrderItem(int totalPrice, Order order, int orderPrice, int quantity, Item item) {
        this.totalPrice = totalPrice;
        this.order = order;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.item = item;
    }
}
