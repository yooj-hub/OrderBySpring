package yooj.toyproject.orderbyspring.repository;


import yooj.toyproject.orderbyspring.web.dto.OrderListQueryDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom {
    List<OrderListQueryDto> findOrderListQueryDtoByMemberId(Long memberId);
    Integer findOrderPrice(Long orderId);
}
