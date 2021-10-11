package yooj.toyproject.orderbyspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String brand;
    private LocalDate manufacturingDate;

}
