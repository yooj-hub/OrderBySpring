package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yooj.toyproject.orderbyspring.domain.OrderStatus;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    private Long orderId;
    private OrderStatus status;
}
