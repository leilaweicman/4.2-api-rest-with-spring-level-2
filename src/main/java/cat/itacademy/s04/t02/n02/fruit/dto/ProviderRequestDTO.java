package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;

public record ProviderRequestDTO(
        @NotBlank(message = "Provider name cannot be empty.")
        String name,

        @NotBlank(message = "Provider country cannot be empty.")
        String country
) {}
