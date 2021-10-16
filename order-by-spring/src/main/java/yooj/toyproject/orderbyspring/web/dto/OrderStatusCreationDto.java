package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderStatusCreationDto {
    private List<OrderStatusDto> orderStatusForm = new ArrayList<>();
    public void addOrderStatusDto(OrderStatusDto orderStatusDto){
        this.orderStatusForm.add(orderStatusDto);
    }

}
