package yooj.toyproject.orderbyspring.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.web.login.LoginMemberDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @AfterEach
    void after(){
        memberRepository.deleteAll();
    }
    @BeforeEach
    void before(){
        memberRepository.deleteAll();
    }

    @Test
    void 로그인() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        memberRepository.save(member1);

        //when
        LoginMemberDto loginMember = memberRepository.login("member1", "member1!");
        Address loginMemberAddress = loginMember.getAddress();


        //then
        // 정상 로그인 테스트
        assertThat(loginMember.getLoginId()).isEqualTo("member1");
        assertThat(loginMember.getId()).isEqualTo(member1.getId());
        assertThat(loginMemberAddress.getCity()).isEqualTo(address.getCity());
        assertThat(loginMemberAddress.getZipcode()).isEqualTo(address.getZipcode());
        assertThat(loginMemberAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(loginMember.getUsername()).isEqualTo(member1.getUsername());
        // 비밀 번호가 틀린 경우
        assertThat(memberRepository.login("member1","member")).isNull();
//        assertThrows(IllegalArgumentException.class, () -> memberRepository.login("member1", "member"));

    }

}
