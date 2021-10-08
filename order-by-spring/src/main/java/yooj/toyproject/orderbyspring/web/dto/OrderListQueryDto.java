package yooj.toyproject.orderbyspring.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.OrderStatus;

import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListQueryDto {
    private Long orderId;
    private String username;
    private OrderStatus status;
    @Embedded
    private Address address;
    private LocalDateTime orderDate;
}
