package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemCreationDto {
    private List<OrderItemDto> orderItemForm= new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemForm.add(orderItemDto);
    }

}
