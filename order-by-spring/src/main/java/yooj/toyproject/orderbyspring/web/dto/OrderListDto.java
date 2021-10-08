package yooj.toyproject.orderbyspring.web.dto;

import lombok.Data;
import yooj.toyproject.orderbyspring.BaseEntity;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.OrderStatus;
import yooj.toyproject.orderbyspring.service.OrderService;

import java.time.LocalDateTime;

@Data
public class OrderListDto {
    private Long orderId;
    private String username;
    private OrderStatus status;
    private Address address;
    private int totalPrice;
    private LocalDateTime orderDate;


    public OrderListDto(Long orderId, String username, OrderStatus status, Address address, int totalPrice, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.username = username;
        this.status = status;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }
}
