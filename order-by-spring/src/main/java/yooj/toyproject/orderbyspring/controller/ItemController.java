package yooj.toyproject.orderbyspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.domain.item.Instrument;
import yooj.toyproject.orderbyspring.domain.item.Item;
import yooj.toyproject.orderbyspring.service.ItemSearch;
import yooj.toyproject.orderbyspring.service.ItemService;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;
import yooj.toyproject.orderbyspring.web.dto.InstrumentDto;
import yooj.toyproject.orderbyspring.web.dto.OrderPriceAndQuantity;
import yooj.toyproject.orderbyspring.web.dto.OrderQuantityCreationForm;
import yooj.toyproject.orderbyspring.web.form.InstrumentAddForm;
import yooj.toyproject.orderbyspring.web.form.InstrumentEditForm;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j

public class ItemController {
    private final ItemService itemService;

    @GetMapping("item/add")
    public String goItemAddHome() {
        return "item/itemAddHome";
    }

    @GetMapping("item/add/instrument")
    public String goInstrumentAddForm(Model model) {
        model.addAttribute("form", new InstrumentAddForm());
        return "item/instrumentAddForm";
    }

    @PostMapping("item/add/instrument")
    public String goInstrumentAdd(Model model, @Validated @ModelAttribute("form") InstrumentAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "item/instrumentAddForm";
        }
        if (form.getPrice() < 0) {
            bindingResult.rejectValue("price", "value error", "0이상의 값을 입력하시오");
        }
        if (form.getStockQuantity() <= 0) {
            bindingResult.rejectValue("stockQuantity", "value error", "1이상의 값을 입력하시오");
        }
        if (bindingResult.hasErrors()) {
            return "item/instrumentAddForm";
        }
        itemService.save(new Instrument(form.getName(), form.getPrice(), form.getStockQuantity(), form.getBrand(), form.getManufacturingDate()));
        return "redirect:";
    }

    @GetMapping(value = {"item/instrument/itemList"})
    public String goInstrumentList(@Login LoginMemberDto loginMember, Model model, @ModelAttribute(name = "itemSearch") ItemSearch itemSearch) {
        String search = itemSearch.getSearch();
        if (search == null) {
            search = "";
        }
        List<InstrumentDto> allInstrumentDto;
        OrderQuantityCreationForm form = new OrderQuantityCreationForm();
        log.info(search);
        if (StringUtils.hasText(search)) {
            allInstrumentDto = itemService.findAllByName(itemSearch).stream().map(i ->
                            {
                                Instrument ins = (Instrument) i;
                                return new InstrumentDto(i.getId(), i.getName(), i.getPrice(), i.getStockQuantity(), ins.getBrand(), ins.getManufacturingDate());
                            }
                    )
                    .collect(Collectors.toList());
            for (InstrumentDto instrumentDto : allInstrumentDto) {
                form.addOrderPriceAndQuantity(new OrderPriceAndQuantity(instrumentDto.getId(), instrumentDto.getPrice(), 0));
            }
        } else {
            allInstrumentDto = itemService.findAllInstrumentDto();
            for (InstrumentDto instrumentDto : allInstrumentDto) {
                form.addOrderPriceAndQuantity(new OrderPriceAndQuantity(instrumentDto.getId(), instrumentDto.getPrice(), 0));
            }
        }
        itemSearch = new ItemSearch();
        model.addAttribute("address", loginMember.getAddress());
        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("itemList", allInstrumentDto);
        model.addAttribute("form", form);
        return "item/instrumentInfo";
    }

    @GetMapping("item/edit")
    public String goSelectItemKind() {
        return "item/itemEditHome";
    }

    @GetMapping("item/edit/instrument")
    public String goInstrumentEdit(Model model, @ModelAttribute(name = "itemSearch") ItemSearch itemSearch) {
        String search = itemSearch.getSearch();
        if (search == null) {
            search = "";
        }
        List<InstrumentDto> allInstrumentDto;
        OrderQuantityCreationForm form = new OrderQuantityCreationForm();
        log.info(search);
        if (StringUtils.hasText(search)) {
            allInstrumentDto = itemService.findAllByName(itemSearch).stream().map(i ->
                            {
                                Instrument ins = (Instrument) i;
                                return new InstrumentDto(i.getId(), i.getName(), i.getPrice(), i.getStockQuantity(), ins.getBrand(), ins.getManufacturingDate());
                            }
                    )
                    .collect(Collectors.toList());
            for (InstrumentDto instrumentDto : allInstrumentDto) {
                form.addOrderPriceAndQuantity(new OrderPriceAndQuantity(instrumentDto.getId(), instrumentDto.getPrice(), 0));
            }
        } else {
            allInstrumentDto = itemService.findAllInstrumentDto();
            for (InstrumentDto instrumentDto : allInstrumentDto) {
                form.addOrderPriceAndQuantity(new OrderPriceAndQuantity(instrumentDto.getId(), instrumentDto.getPrice(), 0));
            }
        }
        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("itemList", allInstrumentDto);
        model.addAttribute("form", form);

        return "item/instrumentEditInfo";
    }

    @GetMapping("item/edit/instrument/{id}")
    public String goEditInstrumentForm(@PathVariable Long id, Model model) {
        Instrument item = (Instrument) itemService.findById(id);
        InstrumentEditForm form = new InstrumentEditForm(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity(), item.getBrand(), item.getManufacturingDate());
        model.addAttribute("form", form);
        return "item/instrumentEditForm";
    }

    @PostMapping("item/edit/instrument/{id}")
    public String itemEdit(@PathVariable Long id, @Validated @ModelAttribute("form") InstrumentEditForm form, BindingResult bindingResult) {
        if (form.getPrice() < 0) {
            bindingResult.rejectValue("price", "value error", "0이상의 값을 입력하시오");
        }
        if (form.getStockQuantity() <= 0) {
            bindingResult.rejectValue("stockQuantity", "value error", "1이상의 값을 입력하시오");
        }
        if (bindingResult.hasErrors()) {
            return "item/instrumentEditForm";
        }
        itemService.instrumentChange(id, form.getName(), form.getPrice(), form.getStockQuantity(), form.getBrand(), form.getManufacturingDate());
        return "redirect:";
    }
}
