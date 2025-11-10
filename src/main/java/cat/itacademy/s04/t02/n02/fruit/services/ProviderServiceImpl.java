package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderCountryException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

    ProviderRepository providerRepository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
    @Override
    public Provider createProvider(Provider provider) {
        if (provider.getName() == null || provider.getName().trim().isEmpty()) {
            throw new InvalidProviderNameException("Provider name cannot be empty or null.");
        }

        if (provider.getCountry() == null || provider.getCountry().trim().isEmpty()) {
            throw new InvalidProviderCountryException("Provider country cannot be empty or null.");
        }

        if (providerRepository.existsByName(provider.getName())) {
            throw new DuplicateProviderNameException(
                    "Provider with name '" + provider.getName() + "' already exists.");
        }

        return providerRepository.save(provider);
    }
}
