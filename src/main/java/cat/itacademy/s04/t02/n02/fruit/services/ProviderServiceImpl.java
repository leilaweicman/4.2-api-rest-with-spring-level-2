package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
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
    public ProviderResponseDTO createProvider(ProviderRequestDTO dto) {
        Provider provider = new Provider(dto.getName(), dto.getCountry());
        providerValidator.validate(provider);

        Provider saved = providerRepository.save(provider);
        return new ProviderResponseDTO(saved.getId(), saved.getName(), saved.getCountry());
    }
}
