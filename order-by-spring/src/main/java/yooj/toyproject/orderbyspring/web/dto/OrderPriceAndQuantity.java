package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderPriceAndQuantity {
    private Long itemId;
    private int price;
    private int orderQuantity;
}
