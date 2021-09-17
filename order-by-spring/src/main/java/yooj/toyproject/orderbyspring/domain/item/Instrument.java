package yooj.toyproject.orderbyspring.domain.item;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instrument extends Item {
    private String brand;
    private LocalDateTime ManufacturingDate;


    public Instrument(String name, int price, int stockQuantity, String brand, LocalDateTime manufacturingDate) {
        initItem(name, price, stockQuantity);
        this.brand = brand;
        this.ManufacturingDate = manufacturingDate;
    }

    public void updateInstrument(String name, int price, int stockQuantity, String brand, LocalDateTime manufacturingDate) {
        initItem(name, price, stockQuantity);
        this.brand = brand;
        this.ManufacturingDate = manufacturingDate;
    }

}
