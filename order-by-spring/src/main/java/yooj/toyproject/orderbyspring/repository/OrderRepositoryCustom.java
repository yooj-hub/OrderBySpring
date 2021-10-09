package yooj.toyproject.orderbyspring.repository;


import yooj.toyproject.orderbyspring.web.dto.OrderListQueryDto;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderListQueryDto> findOrderListQueryDtoByMemberId(Long memberId);
    Integer findOrderPrice(Long orderId);
    Long findMemberIdByOrderId(Long orderId);
}
