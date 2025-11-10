package cat.itacademy.s04.t02.n02.fruit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponseDTO {
    private Long id;
    private String name;
    private String country;
}