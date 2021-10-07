package yooj.toyproject.orderbyspring.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String zipcode;
}
