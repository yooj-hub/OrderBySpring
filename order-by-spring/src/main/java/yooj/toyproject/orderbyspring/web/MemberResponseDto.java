package yooj.toyproject.orderbyspring.web;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import yooj.toyproject.orderbyspring.domain.Address;
import yooj.toyproject.orderbyspring.domain.Member;

@Data

public class MemberResponseDto {

    private Long id;
    private String username;
    private String logindId;
    private Address address;

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();;
        this.username = entity.getUsername();
        this.logindId = entity.getLoginId();
        this.address = entity.getAddress();
    }

    @QueryProjection
    public MemberResponseDto(Long id, String username, String logindId, Address address) {
        this.id = id;
        this.username = username;
        this.logindId = logindId;
        this.address = address;
    }
}
