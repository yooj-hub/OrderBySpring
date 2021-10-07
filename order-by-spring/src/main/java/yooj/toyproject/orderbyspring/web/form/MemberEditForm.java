package yooj.toyproject.orderbyspring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yooj.toyproject.orderbyspring.web.LoginMemberDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEditForm {
    @NotNull
    private Long id;
    @NotEmpty
    private String username;
//    @NotEmpty
//    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String currentPassword;

    public MemberEditForm(LoginMemberDto loginMember) {
        this.id = loginMember.getId();
        this.username = loginMember.getUsername();
        this.city = loginMember.getAddress().getCity();
        this.street = loginMember.getAddress().getStreet();
        this.zipcode = loginMember.getAddress().getZipcode();
    }


}
