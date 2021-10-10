package yooj.toyproject.orderbyspring.domain;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Address {
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String zipcode;
    public void changeAddress(Address address){
        this.city=address.getCity();
        this.street= address.getStreet();
        this.zipcode= address.getZipcode();
    }
}
