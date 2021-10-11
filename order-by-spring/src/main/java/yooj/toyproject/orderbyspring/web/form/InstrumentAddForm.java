package yooj.toyproject.orderbyspring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentAddForm {
    @NotEmpty
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stockQuantity;
    @NotEmpty
    private String brand;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;
}
