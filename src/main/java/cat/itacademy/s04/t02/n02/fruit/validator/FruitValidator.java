package cat.itacademy.s04.t02.n02.fruit.validator;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitProviderException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitWeightException;
import org.springframework.stereotype.Component;

@Component
public class FruitValidator {

    public void validate(FruitRequestDTO dto) {
        validateName(dto.name());
        validateWeight(dto.weight());
        validateProviderId(dto.providerId());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFruitNameException("Fruit name cannot be null or blank.");
        }
    }

    private void validateWeight(int weight) {
        if (weight <= 0) {
            throw new InvalidFruitWeightException("Fruit weight must be greater than 0.");
        }
    }

    private void validateProviderId(Long providerId) {
        if (providerId == null) {
            throw new InvalidFruitProviderException("Fruit must have a provider ID.");
        }
    }
}
