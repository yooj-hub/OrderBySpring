package yooj.toyproject.orderbyspring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String zipcode;

}
