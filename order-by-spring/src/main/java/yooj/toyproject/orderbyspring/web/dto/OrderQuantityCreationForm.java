package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuantityCreationForm {
    private List<OrderPriceAndQuantity> orderPriceAndQuantityList = new ArrayList<>();

    public void addOrderPriceAndQuantity(OrderPriceAndQuantity orderPriceAndQuantity) {
        this.orderPriceAndQuantityList.add(orderPriceAndQuantity);
    }


}
