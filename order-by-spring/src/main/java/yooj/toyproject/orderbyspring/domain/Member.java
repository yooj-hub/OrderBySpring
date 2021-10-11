package yooj.toyproject.orderbyspring.domain;


import lombok.*;
import yooj.toyproject.orderbyspring.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(name = "login_id_unique", columnNames = "loginId")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String loginId;

    private String password;
    @Embedded
    private Address address;

    @Builder
    public Member(String username, String loginId, String password, Address address) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.address = address;
    }

    public void changeMember(String username, String password, String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
        this.username = username;
        this.password = password;
    }
}
