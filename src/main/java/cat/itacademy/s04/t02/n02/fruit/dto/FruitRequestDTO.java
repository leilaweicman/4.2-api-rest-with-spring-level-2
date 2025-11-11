package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.*;

public record FruitRequestDTO(
        @NotBlank(message = "Fruit name cannot be empty or null.")
        String name,

        @Positive(message = "Weight must be greater than zero.")
        int weight,

        @NotNull(message = "Provider ID cannot be null.")
        Long providerId
) {}
