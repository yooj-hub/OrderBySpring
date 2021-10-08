package yooj.toyproject.orderbyspring.web.login;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;

@Data
public class LoginMemberDto {

    private Long id;
    private String username;
    private String loginId;
    private Address address;

    public LoginMemberDto(Member entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.loginId = entity.getLoginId();
        this.address = entity.getAddress();
    }

    @QueryProjection
    public LoginMemberDto(Long id, String username, String loginId, Address address) {
        this.id = id;
        this.username = username;
        this.loginId = loginId;
        this.address = address;
    }
}