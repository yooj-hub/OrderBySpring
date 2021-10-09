package yooj.toyproject.orderbyspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yooj.toyproject.orderbyspring.domain.Order;
import yooj.toyproject.orderbyspring.domain.OrderStatus;
import yooj.toyproject.orderbyspring.service.OrderItemService;
import yooj.toyproject.orderbyspring.service.OrderService;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;
import yooj.toyproject.orderbyspring.web.dto.OrderItemDto;
import yooj.toyproject.orderbyspring.web.dto.OrderListDto;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;


    @GetMapping("/order/orderList")
    public String orderList(@Login LoginMemberDto loginMember, Model model, HttpServletRequest request) {
        List<OrderListDto> orderList = orderService.findOrderListDto(loginMember.getId());
        model.addAttribute("orderList", orderList);
        return "/order/orderList";
    }

    @GetMapping("/order/info/{orderId}")
    public String orderInfo(@PathVariable Long orderId, @Login LoginMemberDto loginMember, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderService.findById(orderId);
        if (!orderService.checkMemberId(orderId, loginMember.getId())) {
            redirectAttributes.addFlashAttribute("authorization", "접근권한이 없습니다.");
            return "redirect:/order/orderList";
        }
        orderInfoAndEditParameter(orderId, model, order);
        return "/order/orderInfo";
    }

    @GetMapping("/order/edit/{orderId}")
    public String editOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        orderInfoAndEditParameter(orderId, model, order);
        return "/order/form/orderEditForm";
    }

    @PostMapping("/order/edit/{orderId}")
    public String postEditOrder(@PathVariable Long orderId, Model model
            , @Validated @ModelAttribute(name = "orderItemList") List<OrderItemDto> orderItemDtos,
                                BindingResult bindingResult) {
        return null;
    }

    private void orderInfoAndEditParameter(@PathVariable Long orderId, Model model, Order order) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderItemList", orderItemService.findOrderItemDto(orderId));
        model.addAttribute("status", orderService.findById(orderId).getStatus());
        model.addAttribute("address", order.getAddress());
    }
}
