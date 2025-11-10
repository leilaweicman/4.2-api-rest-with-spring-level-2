package cat.itacademy.s04.t02.n02.fruit.validator;

import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderCountryException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Component;

@Component
public class ProviderValidator {

    private final ProviderRepository providerRepository;

    public ProviderValidator(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public void validate(Provider provider) {
        validateName(provider.getName());
        validateCountry(provider.getCountry());
        validateUniqueName(provider.getName());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProviderNameException("Provider name cannot be empty or null.");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new InvalidProviderCountryException("Provider country cannot be empty or null.");
        }
    }

    private void validateUniqueName(String name) {
        if (providerRepository.existsByName(name)) {
            throw new DuplicateProviderNameException("Provider with name '" + name + "' already exists.");
        }
    }
}
