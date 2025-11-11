package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.FruitValidator;
import org.springframework.stereotype.Service;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;
    private final FruitValidator fruitValidator;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository, FruitValidator fruitValidator) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
        this.fruitValidator = fruitValidator;
    }

    @Override
    public FruitResponseDTO createFruit(FruitRequestDTO dto) {
        fruitValidator.validate(dto);

        Provider provider = providerRepository.findById(dto.providerId()).orElseThrow(() -> new ProviderNotFoundException("Provider with ID " + dto.providerId() + " not found."));

        Fruit fruit = new Fruit(dto.name(), dto.weight(), provider);
        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponseDTO(saved.getId(), saved.getName(), saved.getWeight(), saved.getProvider().getId(), saved.getProvider().getName());
    }
}