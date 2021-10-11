package yooj.toyproject.orderbyspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.service.MemberService;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;
import yooj.toyproject.orderbyspring.web.argumentresolver.Login;
import yooj.toyproject.orderbyspring.web.form.MemberEditForm;
import yooj.toyproject.orderbyspring.web.form.MemberJoinForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("form", new MemberJoinForm());
        return "/member/joinForm";
    }
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute(name = "form") MemberJoinForm form, BindingResult bindingResult) {
        if (memberService.findByLoginId(form.getLoginId()).isPresent()) {
            bindingResult.reject("duplicate Id", "아이디가 중복되었습니다.");
        }
        if (bindingResult.hasErrors()) {
            return "/member/joinForm";
        }
        memberService.save(Member.builder()
                .username(form.getUsername())
                .address(Address.builder()
                        .city(form.getCity())
                        .street(form.getStreet())
                        .zipcode(form.getZipcode())
                        .build())
                .password(form.getPassword())
                .loginId(form.getLoginId())
                .build());
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editForm(@Login LoginMemberDto loginMember, Model model) {
//        log.info("Member's ID {}",loginMember.getId());
        model.addAttribute("form", new MemberEditForm(loginMember));
        return "/member/editForm";
    }

    @PostMapping("/edit")
    public String edit(@Validated @ModelAttribute(name = "form") MemberEditForm form, BindingResult bindingResult, HttpServletRequest request) {
//        log.info("Member's ID {}",form.getId());
        if (!memberService.findByIdPassword(form.getId()).equals(form.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "not equal password", "현재 비밀번호가 일치하지 않습니다.");
        }
        if (bindingResult.hasErrors()) {
            return "/member/editForm";
        }
        memberService.changeMember(form.getId(),
                        form.getUsername(),
                        form.getPassword(),
                        form.getCity(),
                        form.getStreet(),
                        form.getZipcode()
                );
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
