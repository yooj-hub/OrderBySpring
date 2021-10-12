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
import yooj.toyproject.orderbyspring.domain.*;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.service.*;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;
import yooj.toyproject.orderbyspring.web.dto.*;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final MemberService memberService;


    @GetMapping("order/orderList")
    public String orderList(@Login LoginMemberDto loginMember, Model model, HttpServletRequest request) {
        List<OrderListDto> orderList = orderService.findOrderListDto(loginMember.getId());
        model.addAttribute("orderList", orderList);
        return "order/orderList";
    }

    @GetMapping("order/info/{orderId}")
    public String orderInfo(@PathVariable Long orderId, @Login LoginMemberDto loginMember, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderService.findById(orderId);
        if (!orderService.checkMemberId(orderId, loginMember.getId())) {
            redirectAttributes.addFlashAttribute("authorization", "접근권한이 없습니다.");
            return "redirect:/order/orderList";
        }
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderItemList", orderItemService.findOrderItemDto(orderId));
        model.addAttribute("status", orderService.findById(orderId).getStatus());
        model.addAttribute("address", order.getAddress());
        return "order/orderInfo";
    }

    @GetMapping("order/edit/{orderId}")
    public String editOrder(@PathVariable Long orderId, Model model, RedirectAttributes redirectAttributes, @Login LoginMemberDto loginMember) {
        if (!orderService.checkMemberId(orderId, loginMember.getId())) {
            redirectAttributes.addFlashAttribute("authorization", "접근권한이 없습니다.");
            return "redirect:/order/orderList";
        }
        Order order = orderService.findById(orderId);
        model.addAttribute("orderId", orderId);
        OrderItemCreationDto orderItemDto = new OrderItemCreationDto();
        List<Item> itemList = itemService.findByOrderId(orderId);
        List<OrderItemDto> findOrderItemDto = orderItemService.findOrderItemDto(orderId);

        List<Integer> purchasable=new ArrayList<>();
        for(int i=0;i<itemList.size();i++){
            purchasable.add(Integer.sum(findOrderItemDto.get(i).getQuantity(),itemList.get(i).getStockQuantity()));
        }
        findOrderItemDto
                .forEach(orderItemDto::addOrderItemDto);
        model.addAttribute("orderItemListForm", orderItemDto);
        model.addAttribute("status", order.getStatus());
        model.addAttribute("address", order.getAddress());
        model.addAttribute("itemQuantityList",purchasable);
        return "order/form/orderEditForm";
    }

    @PostMapping("order/edit/{orderId}")
    public String postEditOrder(@PathVariable Long orderId, Model model
            , @Validated @ModelAttribute(name = "orderItemListForm") OrderItemCreationDto orderItemDtos,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute("address")Address address
                                ) {
        List<OrderItem> preOrderItemList = orderItemService.findByOrderIdWithItem(orderId);
        List<OrderItemDto> orderItemList = orderItemDtos.getOrderItemForm();
//        for (OrderItemDto orderItemDto : orderItemList) {
//            log.info("{}",orderItemDto);
//        }
        log.info(address.getCity());
        int size = orderItemList.size();
        for (int i = 0; i < size; i++) {
            if (preOrderItemList.get(i).getItem().getStockQuantity() -
                    (orderItemList.get(i).getQuantity() - preOrderItemList.get(i).getQuantity()) < 0) {
                bindingResult.rejectValue("orderItemForm[" + i + "]", "quantity Error", "수량이 부족합니다.");
            }
        }
        if (bindingResult.hasErrors()) {
            List<Item> itemList = itemService.findByOrderId(orderId);
            List<OrderItemDto> findOrderItemDto = orderItemService.findOrderItemDto(orderId);
            List<Integer> purchasable=new ArrayList<>();
            for(int i=0;i<itemList.size();i++){
                purchasable.add(Integer.sum(findOrderItemDto.get(i).getQuantity(),itemList.get(i).getStockQuantity()));
            }
            model.addAttribute("itemQuantityList",purchasable);
            return "order/form/orderEditForm";
        }
        orderItemService.orderItemChange(orderId, orderItemList);
        orderService.changeAddress(orderId,address);
        return "redirect:/order/info/" + orderId;
    }
    @GetMapping("/order")
    public String goItemSelectList(){
        return "item/itemSelectHome";
    }
    @PostMapping("/order")
    public String instrumentOrder(@Login LoginMemberDto loginMember,@ModelAttribute("form")OrderQuantityCreationForm form,
                                  BindingResult bindingResult,
                                  Model model,
                                  @ModelAttribute("itemSearch") ItemSearch itemSearch,
                                  RedirectAttributes redirectAttributes,
                                  @ModelAttribute("address") Address address){
        List<OrderPriceAndQuantity> orderPriceAndQuantityList = form.getOrderPriceAndQuantityList();
        boolean isError=false;
        for(int i=0;i<orderPriceAndQuantityList.size();i++){
            Long itemId = orderPriceAndQuantityList.get(i).getItemId();
            Item item = itemService.findById(itemId);
            if(item.getStockQuantity()<orderPriceAndQuantityList.get(i).getOrderQuantity()){
                redirectAttributes.addFlashAttribute("quantityError","수량이 부족합니다.");
                isError=true;
            }
        }
        if(bindingResult.hasErrors() || isError){
            return "redirect:/item/instrument/itemList";
        }
        Member member = memberService.findById(loginMember.getId());
        Order savedOrder = orderService.save(new Order(member, OrderStatus.WAITING, address));
        int cnt = 0;
        for (OrderPriceAndQuantity orderPriceAndQuantity : orderPriceAndQuantityList) {
            int quantity = orderPriceAndQuantity.getOrderQuantity();
            if(quantity==0){
                continue;
            }
            cnt++;
            int price = orderPriceAndQuantity.getPrice();
            Long itemId = orderPriceAndQuantity.getItemId();
            orderItemService.save(orderItemService.makeOrderItem(savedOrder.getId(), price, quantity, itemId));
        }
        if(cnt==0){
            orderService.deleteOrder(savedOrder);
        }

        return "redirect:/";

    }


}

