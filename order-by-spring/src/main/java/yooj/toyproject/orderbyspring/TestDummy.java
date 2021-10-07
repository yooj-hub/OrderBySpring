package yooj.toyproject.orderbyspring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDummy {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Address address = new Address("seoul", "gwan", "12340");
        Member member = Member.builder()
                .username("esw")
                .loginId("test")
                .password("test!")
                .address(address)
                .build();
        memberRepository.save(member);
    }

}
