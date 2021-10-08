package yooj.toyproject.orderbyspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.service.OrderService;
import yooj.toyproject.orderbyspring.web.dto.OrderListDto;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/order")
    public String orderList(@Login LoginMemberDto loginMember, Model model){
        List<OrderListDto> orderList = orderService.findOrderListDto(loginMember.getId());
        model.addAttribute("orderList", orderList);
        return "/order/orderList";
    }
}
