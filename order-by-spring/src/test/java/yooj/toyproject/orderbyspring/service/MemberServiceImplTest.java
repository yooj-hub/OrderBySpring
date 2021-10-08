package yooj.toyproject.orderbyspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;
import yooj.toyproject.orderbyspring.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;
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
    void 회원가입및조회() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);
        Member member2 = new Member("j", "member2", "member2!", address);

        //when
        Member saveMember1 = memberService.save(member1);
        Member saveMember2 = memberService.save(member2);
        List<Member> members = memberService.findAll();

        //then
        assertThat(memberService.findById(saveMember1.getId())).isEqualTo(member1);
        assertThat(memberService.findById(saveMember2.getId())).isEqualTo(member2);
        assertThat(members).contains(member1, member2);
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void 회원정보_수정() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);

        //when
        Member saveMember = memberService.save(member1);
        member1.changeMember("m",  "member2!", address.getCity(), address.getStreet(), address.getZipcode());
        em.flush();
        em.clear();
        Member findMember = memberService.findById(saveMember.getId());


        //then
        assertThat(findMember.getUsername()).isEqualTo("m");
        assertThat(findMember.getPassword()).isEqualTo("member2!");
    }

    @Test
    void 회원_삭제() throws Exception {
        //given
        Address address = new Address("seoul", "gwan", "12340");
        Member member1 = new Member("y", "member1", "member1!", address);

        //when
        Member savedMember = memberService.save(member1);
        em.flush();
        em.clear();
        memberService.deleteMember(savedMember);
        //then
        assertThat(memberService.findById(savedMember.getId())).isNull();
    }




}
