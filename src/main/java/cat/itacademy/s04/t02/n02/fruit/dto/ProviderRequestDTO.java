package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRequestDTO {

    @NotBlank(message = "Provider name cannot be empty.")
    private String name;

    @NotBlank(message = "Provider country cannot be empty.")
    private String country;
}
