package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.ProviderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

    ProviderRepository providerRepository;
    ProviderValidator providerValidator;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderValidator providerValidator) {
        this.providerRepository = providerRepository;
        this.providerValidator = providerValidator;
    }
    @Override
    public Provider createProvider(Provider provider) {
        providerValidator.validate(provider);
        return providerRepository.save(provider);
    }
}
