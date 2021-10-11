package yooj.toyproject.orderbyspring.domain.item;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instrument extends Item {
    private String brand;
    private LocalDate manufacturingDate;


    public Instrument(String name, int price, int stockQuantity, String brand, LocalDate manufacturingDate) {
        initItem(name, price, stockQuantity);
        this.brand = brand;
        this.manufacturingDate = manufacturingDate;
    }

    public void updateInstrument(String name, int price, int stockQuantity, String brand, LocalDate manufacturingDate) {
        initItem(name, price, stockQuantity);
        this.brand = brand;
        this.manufacturingDate = manufacturingDate;
    }

}
