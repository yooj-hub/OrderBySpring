package yooj.toyproject.orderbyspring.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int totalPrice;
    private int orderPrice;
    private int quantity;
    private String itemName;
    private String itemBrand;

    public OrderItemDto(Long id, int totalPrice, int orderPrice, int quantity, String itemName,String itemBrand) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemBrand=itemBrand;
    }
}
