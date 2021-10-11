package yooj.toyproject.orderbyspring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentEditForm {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int stockQuantity;
    @NotEmpty
    private String brand;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;
}
